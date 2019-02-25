package io.tanjundang.study.test;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Author: TanJunDang
 * @Date: 2019/2/25 14:24
 * @Description:
 */
@Entity
public class IDCard {
    @Unique
    @Id
    Long id;

    @Generated(hash = 329701778)
    public IDCard(Long id) {
        this.id = id;
    }

    @Generated(hash = 1276747893)
    public IDCard() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
