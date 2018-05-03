package com.ctsig.order.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 国家
 *
 * @author wangan
 * @date 2018/5/3
 */
@Data
@Entity
public class Country implements Serializable {

    private static final long serialVersionUID = 5744744520097736469L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 名称
     */
    @Column(name = "countryname")
    private String countryName;

    /**
     * 代码
     */
    @Column(name = "countrycode")
    private String countryCode;


    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", countryName='" + countryName + '\'' +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }
}
