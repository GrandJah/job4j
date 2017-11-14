package ru.job4j.orderbook;

/**
 * Операция производимая в книге заказов.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 15.11.2017
 */
class Operation {
    /**
     * наличие записи заказа.
     */
    private boolean entryExist = false;
    /**
     * Идентификационная запись заказа.
     */
    private Entry entry = null;
    /**
     * Наличие атрибута заказа, при отсутствии происходит удаление.
     */
    private boolean attributeExist = false;
    /**
     * Атрибут операции добавляемого заказа.
     */
    private Attribute attribute = null;

    /**
     * строка текущего заказа.
     */
    private String readString;

    /**
     * @param readString строка текущего заказа.
     */
    Operation(String readString) {
        this.readString = readString;
        init();
    }

    /**
     * инициализатор параметров операции по строке заказа.
     */
    private void init() {
        if (this.readString.indexOf("AddOrder") > 0) {
            attributeExist = true;
            entryExist = true;
        } else if (this.readString.indexOf("DeleteOrder") > 0) {
            entryExist = true;
        }
        if (entryExist) {
            entry = new Entry(extract("book"),
                    Integer.parseInt(extract("orderId")));
        }
        if (attributeExist) {
            attribute = new Attribute(
                    extract("operation").equals("BUY"),
                    Double.parseDouble(extract("price")),
                    Integer.parseInt(extract("volume")));
        }
    }

    /** парсер строки на название параметра.
     * @param ss название параметра
     * @return строка значение параметра
     */
    private String extract(String ss) {
        int pos = this.readString.indexOf(ss + "=\"") + ss.length() + 2;
        return this.readString.substring(pos, this.readString.indexOf("\"", pos));
    }

    /**
     * Getter.
     *
     * @return entryExist entryExist
     */
    boolean isEntryExist() {
        return this.entryExist;
    }

    /**
     * Getter.
     *
     * @return entry entry
     */
    Entry getEntry() {
        return this.entry;
    }

    /**
     * Getter.
     *
     * @return attributeExist attributeExist
     */
    boolean isAttributeExist() {
        return this.attributeExist;
    }

    /**
     * Getter.
     *
     * @return attribute attribute
     */
    Attribute getAttribute() {
        return this.attribute;
    }
}