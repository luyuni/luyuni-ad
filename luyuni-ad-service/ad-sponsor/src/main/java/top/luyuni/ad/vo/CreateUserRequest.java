package top.luyuni.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    private String username;

    public boolean validate(){
        return StringUtils.isNotEmpty(username);
    }
}
