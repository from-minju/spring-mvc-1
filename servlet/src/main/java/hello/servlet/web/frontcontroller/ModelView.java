package hello.servlet.web.frontcontroller;

import java.util.HashMap;
import java.util.Map;

/*
* 응답결과로 뷰 이름, 뷰에 전달한 Model 데이터를 포함하는 ModelView객체.
* 뷰의 이름과 뷰를 렌더링할 때 필요한 model 객체를 가지고 있다.
* */
public class ModelView {
    private String viewName;
    private Map<String, Object> model = new HashMap<>();

    public ModelView(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }
}
