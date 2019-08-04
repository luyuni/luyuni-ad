package top.luyuni.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeUnitResponse {
    /**
     * CreativeUnit的主键
     */
    private List<Long> ids;
}
