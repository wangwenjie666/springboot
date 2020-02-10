package org.spring.demo.log.common;

/**
 * 建造者模式
 *
 * @author wangwenjie
 * @date 2020-01-25
 */
public class BuilderMode {
    private String name;
    private String password;

    public static class Builder {

        private String name;
        private String password;

        public Builder buildName(String name) {
            this.name = name;
            return this;
        }

        public Builder buildPassword(String password) {
            this.password = password;
            return this;
        }

        public BuilderMode build(){
            return new BuilderMode(this);
        }
    }

    public BuilderMode(Builder builder){
        this.name = builder.name;
        this.password = builder.password;
    }

    @Override
    public String toString() {
        return "BuilderMode{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public static void main(String[] args) {
        BuilderMode mode = new Builder()
                .buildName("张三")
                .buildPassword("123").build();
        System.out.println(mode);
    }
}
