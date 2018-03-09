package ru.job4j.pretty_interface.action;

import org.json.simple.JSONObject;
import ru.job4j.data_base.JSONConvert;

import javax.servlet.http.HttpSession;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 09.03.2018
 */
public interface AjaxAction {
    /**
     * Action.
     * @param data data object
     * @return answer
     */
    JSONConvert action(JSONObject data, HttpSession session);
}
