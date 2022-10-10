package com.wandoo.hotel.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    public BaseEntity() {
    }

    public BaseEntity(Builder<?> b) {
        this.id = b.id;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }


    public static  class Builder<T extends Builder<T>> {
        protected Long id;

        public static Builder aBaseEntity() {
            return new Builder();
        }

        public T id(Long id) {
            this.id = id;
            return (T) this;
        }

        public BaseEntity build() {
            return new BaseEntity(this);
        }
    }
}
