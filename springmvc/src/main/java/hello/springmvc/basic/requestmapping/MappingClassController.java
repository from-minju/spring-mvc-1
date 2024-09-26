package hello.springmvc.basic.requestmapping;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mapping/users")
public class MappingClassController {
    /**
     * 회원목록 조회: GET
     * 회원 등록 : POST
     * 회원 조회 : GET
     * 회원 수정 : PATCH
     * 회원 삭제 : DELETE
     */


    /**
     * GET /mapping/users
     */
    @GetMapping
    public String users() {
        return "get user";
    }

    /**
     * POST /mapping/users
     */
    @PostMapping
    public String addUser() {
        return "post user";
    }
    /**
     * GET /mapping/users/{userId}
     */
    @GetMapping("/{userId}")
    public String findUser(@PathVariable("userId") String userId) {
        return "get userId=" + userId;
    }
    /**
     * PATCH /mapping/users/{userId}
     */
    @PatchMapping("/{userId}")
    public String updateUser(@PathVariable("userId") String userId) {
        return "update userId=" + userId;
    }
    /**
     * DELETE /mapping/users/{userId}
     */
    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable("userId") String userId) {
        return "delete userId=" + userId;
    }

}
