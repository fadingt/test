package service.impl;

public class OpStatus {
    enum OperateType {
        NOT_MATCH("不匹配", -4), NOT_NULL("不允许空", -3), NOT_REPEAT("不允许重复", -2), NOT_PROP("无此属性", -1), SUCCESS("成功", 0);
        private String desc;
        private int num;

        private OperateType(String desc, int num) {
            this.desc = desc;
            this.num = num;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
