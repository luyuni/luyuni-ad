package top.luyuni.ad.entity;

import top.luyuni.ad.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ad_user")
public class AdUser {
    /**
     * 自增主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 用户名
     */
    @Basic // 这是默认的
    @Column(name = "username", nullable = false)
    private String username;

    /**
     * 给用户生成的token
     */
    @Basic
    @Column(name = "token", nullable = false)
    private String token;

    /**
     * 用户的状态
     */
    @Basic
    @Column(name = "user_status", nullable = false)
    private Integer userStatus;

    @Basic
    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @Basic
    @Column(name = "update_time", nullable = false)
    private Date updateTime;

    public AdUser(String username, String token) {

        this.username = username;
        this.token = token;
        this.userStatus = CommonStatus.VALID.getStatus();
        this.createTime = new Date();
        this.updateTime = this.createTime;
    }
}
