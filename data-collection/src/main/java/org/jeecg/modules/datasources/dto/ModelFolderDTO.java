package org.jeecg.modules.datasources.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.datasources.model.WaterfallFolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author selegant
 */
@NoArgsConstructor
@Data
public class ModelFolderDTO {


    private String title;
    private String key;
    private String value;
    private SlotsBean slots;
    private Integer level;
    private List<ModelFolderDTO> children;
    private Integer parentId = 0;
    private String remark;

    public ModelFolderDTO(WaterfallFolder waterfallFolder) {
        this.setTitle(waterfallFolder.getFolderName());
        this.setKey(waterfallFolder.getId().toString());
        this.setValue(waterfallFolder.getId().toString());
        this.setSlots(new SlotsBean());
        this.setLevel(1);
        this.setRemark(waterfallFolder.getRemark());
        this.setParentId(waterfallFolder.getParentId());
    }

    public ModelFolderDTO(WaterfallFolder waterfallFolder,Integer level) {
        this.setTitle(waterfallFolder.getFolderName());
        this.setKey(waterfallFolder.getId().toString());
        this.setValue(waterfallFolder.getId().toString());
        this.setSlots(new SlotsBean());
        this.setLevel(level);
        this.setRemark(waterfallFolder.getRemark());
        this.setParentId(waterfallFolder.getParentId());
    }

    public ModelFolderDTO(WaterfallFolder waterfallFolder,List<WaterfallFolder> childrenList) {
        this.setTitle(waterfallFolder.getFolderName());
        this.setKey(waterfallFolder.getId().toString());
        this.setSlots(new SlotsBean());
        this.setValue(waterfallFolder.getId().toString());
        this.setRemark(waterfallFolder.getRemark());
        this.setParentId(waterfallFolder.getParentId());
        List<ModelFolderDTO> list = new ArrayList<>();
        childrenList.forEach(c->{
            list.add(new ModelFolderDTO(c,2));
        });
        this.setChildren(list);
    }


    @Data
    public static class SlotsBean {
        private String icon;
        public SlotsBean() {
            this.icon = "wenjianjia";
        }
    }

}
