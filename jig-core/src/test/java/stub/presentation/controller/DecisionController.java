package stub.presentation.controller;

import org.springframework.stereotype.Controller;

/**
 * 分岐のあるコントローラー
 */
@Controller
public class DecisionController {

    void 分岐のあるメソッド(Object 条件) {
        if (条件 == null) {
            throw new NullPointerException();
        }
    }
}
