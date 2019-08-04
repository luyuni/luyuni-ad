package top.luyuni.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.luyuni.ad.entity.Creative;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeRequest {
    private String name;
    private Integer type;
    private Integer materialType;
    private Integer height;
    private Integer width;
    private Long size;
    private Integer duration;
    private Long userId;
    private String url;

    public Creative convertToEntity(){
        Creative creative = new Creative();
        creative.setName(name);
        creative.setType(type);
        creative.setMaterialType(materialType);
        creative .setHeight(height);
        creative.setWidth(width);
        creative.setSize(size);
        creative.setDuration(duration);
        creative.setUserId(userId);
        creative.setUrl(url);
        creative.setCreateTime(new Date());
        creative.setUpdateTime(creative.getCreateTime());
        return creative;
    }
}
