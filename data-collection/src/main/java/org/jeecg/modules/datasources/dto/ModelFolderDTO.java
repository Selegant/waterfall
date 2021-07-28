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
    private List<ModelFolderDTO> children;

    public ModelFolderDTO(WaterfallFolder waterfallFolder) {
        this.setTitle(waterfallFolder.getFolderName());
        this.setKey(waterfallFolder.getId().toString());
        this.setValue(waterfallFolder.getId().toString());
        this.setSlots(new SlotsBean());
    }

    public ModelFolderDTO(WaterfallFolder waterfallFolder,List<WaterfallFolder> childrenList) {
        this.setTitle(waterfallFolder.getFolderName());
        this.setKey(waterfallFolder.getId().toString());
        this.setSlots(new SlotsBean());
        this.setValue(waterfallFolder.getId().toString());
        List<ModelFolderDTO> list = new ArrayList<>();
        childrenList.forEach(c->{
            list.add(new ModelFolderDTO(c));
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
