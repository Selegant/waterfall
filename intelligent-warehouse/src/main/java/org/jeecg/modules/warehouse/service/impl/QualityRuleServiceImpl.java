package org.jeecg.modules.warehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.warehouse.model.WaterfallModel;
import org.jeecg.modules.warehouse.model.WaterfallQualityRule;
import org.jeecg.modules.warehouse.model.WaterfallQualityRuleField;
import org.jeecg.modules.warehouse.model.WaterfallQualityRuleFieldType;
import org.jeecg.modules.warehouse.dto.WaterfallQualityRuleDTO;
import org.jeecg.modules.warehouse.mapper.WaterfallModelMapper;
import org.jeecg.modules.warehouse.mapper.WaterfallQualityRuleFieldMapper;
import org.jeecg.modules.warehouse.mapper.WaterfallQualityRuleFieldTypeMapper;
import org.jeecg.modules.warehouse.mapper.WaterfallQualityRuleMapper;
import org.jeecg.modules.warehouse.service.IQualityRuleService;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author liansongye
 * @create 2021/9/2 5:38 下午
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class QualityRuleServiceImpl implements IQualityRuleService {

    @Autowired
    private WaterfallModelMapper waterfallModelMapper;

    @Autowired
    private WaterfallQualityRuleMapper ruleMapper;

    @Autowired
    private WaterfallQualityRuleFieldMapper ruleFieldMapper;

    @Autowired
    private WaterfallQualityRuleFieldTypeMapper ruleFieldTypeMapper;

    @Override
    public IPage<WaterfallQualityRule> queryRulePage(Page<WaterfallQualityRule> page, WaterfallQualityRule rule) {
        LambdaQueryWrapper<WaterfallQualityRule> queryWrapper = ruleQueryCondition(rule);

        return ruleMapper.selectPage(page, queryWrapper);
    }

    @Override
    public void saveRule(WaterfallQualityRuleDTO ruleDto) {
        checkQualityRuleDTOParams(ruleDto);
        WaterfallQualityRule ruleEntity = ruleDto.toEntity();
        ruleEntity.setEnableFlag(false);
        ruleEntity.setDelFlag(false);
        ruleEntity.setCreateTime(new Date());
        ruleEntity.setUpdateTime(new Date());
        if(WaterfallQualityRuleDTO.RULE_PUBLIC.equals(ruleDto.getRuleType())) {
            ruleEntity.setModelId(null);
        }
        ruleMapper.insert(ruleEntity);

        ruleDto.getRuleFields().stream().forEach(e -> {
            e.setRuleId(ruleEntity.getId());
            e.setDelFlag(false);
            e.setCreateTime(new Date());
            e.setUpdateTime(new Date());
            ruleFieldMapper.insert(e);
        });
    }

    @Override
    public void updateRule(WaterfallQualityRuleDTO ruleDto) {
        checkQualityRuleDTOParams(ruleDto);
        WaterfallQualityRule ruleEntity = ruleDto.toEntity();
        //todo 不支持类别改动
        ruleEntity.setRuleType(null);
        ruleEntity.setUpdateTime(null);
        ruleEntity.setUpdateTime(new Date());
        ruleMapper.updateByPrimaryKeySelective(ruleEntity);


        LambdaQueryWrapper<WaterfallQualityRuleField> fieldQueryWrapper = new LambdaQueryWrapper<>();
        fieldQueryWrapper.eq(WaterfallQualityRuleField::getRuleId, ruleDto.getId());
        ruleFieldMapper.delete(fieldQueryWrapper);
        ruleDto.getRuleFields().stream().forEach(e -> {
            e.setId(null);
            e.setRuleId(ruleEntity.getId());
            if (e.getDelFlag() == null) {
                e.setDelFlag(false);
            }
            if (e.getCreateTime() == null) {
                e.setCreateTime(new Date());
            }
            e.setUpdateTime(new Date());
            ruleFieldMapper.insert(e);
        });
    }


    @Override
    public void deleteRule(Integer id) {
        WaterfallQualityRule rule = new WaterfallQualityRule();
        rule.setId(id);
        rule.setDelFlag(true);
        rule.setUpdateTime(new Date());
        ruleMapper.updateByPrimaryKeySelective(rule);
    }

    @Override
    public List<WaterfallQualityRuleFieldType> queryRuleFieldTypeList() {
        LambdaQueryWrapper<WaterfallQualityRuleFieldType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(WaterfallQualityRuleFieldType::getTypeCode, WaterfallQualityRuleFieldType::getTypeName)
                .eq(WaterfallQualityRuleFieldType::getDelFlag, false);
        return ruleFieldTypeMapper.selectList(queryWrapper);
    }

    @Override
    public WaterfallQualityRuleDTO ruleInfo(Integer id) {
        WaterfallQualityRuleDTO res = new WaterfallQualityRuleDTO();
        WaterfallQualityRule rule = ruleMapper.selectByPrimaryKey(id);
        res.toDto(rule);

        LambdaQueryWrapper<WaterfallQualityRuleField> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WaterfallQualityRuleField::getRuleId, id)
                .eq(WaterfallQualityRuleField::getDelFlag, false);
        res.setRuleFields(ruleFieldMapper.selectList(queryWrapper));
        return res;
    }

    @Override
    public void updateRuleEnableSatus(Integer id, boolean enable) {
        WaterfallQualityRule ruleEntity = new WaterfallQualityRule();
        ruleEntity.setId(id);
        ruleEntity.setEnableFlag(enable);
        ruleEntity.setUpdateTime(new Date());
        ruleMapper.updateByPrimaryKeySelective(ruleEntity);
    }


    private LambdaQueryWrapper<WaterfallQualityRule> ruleQueryCondition(WaterfallQualityRule rule) {
        LambdaQueryWrapper<WaterfallQualityRule> queryWrapper = new LambdaQueryWrapper<>();
        if (rule == null) {
            return queryWrapper;
        }
        if (rule.getId() != null) {
            queryWrapper.eq(WaterfallQualityRule::getId, rule.getId());
        }
        if (StringUtils.isNotBlank(rule.getRuleName())) {
            queryWrapper.like(WaterfallQualityRule::getRuleName, rule.getRuleName());
        }

        if (rule.getDelFlag() != null) {
            queryWrapper.eq(WaterfallQualityRule::getDelFlag, rule.getDelFlag());
        }

        if (rule.getRuleType() != null) {
            if (rule.getRuleType().equals(WaterfallQualityRuleDTO.RULE_PRIVATE)) {
                if (rule.getModelId() != null) {
                    queryWrapper.eq(WaterfallQualityRule::getModelId, rule.getModelId());
                }
                queryWrapper.eq(WaterfallQualityRule::getRuleType, WaterfallQualityRuleDTO.RULE_PRIVATE);
            } else {
                queryWrapper.eq(WaterfallQualityRule::getRuleType, WaterfallQualityRuleDTO.RULE_PUBLIC);
            }
        } else {
            queryWrapper.and(wq -> {
                if (rule.getModelId() != null) {
                    wq.eq(WaterfallQualityRule::getModelId, rule.getModelId());
                }
                wq.eq(WaterfallQualityRule::getRuleType, WaterfallQualityRuleDTO.RULE_PRIVATE);
                wq.or().eq(WaterfallQualityRule::getRuleType, WaterfallQualityRuleDTO.RULE_PUBLIC);
            });
        }
        return queryWrapper;
    }


    /**
     * 参数校验
     *
     * @param ruleDto
     */
    private void checkQualityRuleDTOParams(WaterfallQualityRuleDTO ruleDto) {
        if (ruleDto.getRuleType() == null) {
            throw new JeecgBootException("质量规则类型不存在！");
        }
        if (StringUtils.isBlank(ruleDto.getRuleName())) {
            throw new JeecgBootException("质量规则名为空！");
        }
        if (ruleDto.getRuleType().equals(WaterfallQualityRuleDTO.RULE_PRIVATE) && ruleDto.getModelId() == null) {
            throw new JeecgBootException("质量规则id不存在！");
        }
        if (ruleDto.getId() != null) {
            WaterfallQualityRule oldRule = ruleMapper.selectByPrimaryKey(ruleDto.getId());
            if (oldRule == null || oldRule.getDelFlag() == true) {
                throw new JeecgBootException("质量规则不存在！");
            }
        }
        LambdaQueryWrapper<WaterfallQualityRule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WaterfallQualityRule::getRuleName, ruleDto.getRuleName())
                .eq(WaterfallQualityRule::getDelFlag, false);
        if (ruleDto.getRuleType().equals(WaterfallQualityRuleDTO.RULE_PRIVATE)) {
            WaterfallModel model = waterfallModelMapper.selectByPrimaryKey(ruleDto.getModelId());
            if (model == null || model.getDelFlag() == true) {
                throw new JeecgBootException("所属模型不存在！");
            }
            queryWrapper.eq(WaterfallQualityRule::getModelId, ruleDto.getModelId());
        }

        List<WaterfallQualityRule> waterfallQualityRules = ruleMapper.selectList(queryWrapper);
        if (waterfallQualityRules.size() > 0) {
            if (waterfallQualityRules.size() > 1 || !waterfallQualityRules.get(0).getId().equals(ruleDto.getId())) {
                throw new JeecgBootException("质量规则名重复！");
            }
        }
    }
}
