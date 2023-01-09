package KangWCB.comgram.ex;

import KangWCB.comgram.error.ErrorResult;
import KangWCB.comgram.ex.member.MemberLoginEx;
import KangWCB.comgram.ex.member.MemberRegisterEx;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ExAdvice {

    /**
     * 등록폼 error 처리
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MemberRegisterEx.class)
    public ErrorResult memberRegisterEx(MemberRegisterEx e){
        log.error("member 등록 이상 : {}", e.getMessage());
        return new ErrorResult("BAD",e.getMessage());
    }

    /**
     * @Vaild bindingResult 로 처리
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors()
                .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));
        log.error("member error: {}", errors);
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * 로그인 에러 처리
     */
    @ExceptionHandler(MemberLoginEx.class)
    public ResponseEntity<Map<String, String>> memberLoginEx(MemberLoginEx e){
        Map<String, String> errors = new HashMap<>();
        errors.put("login_error", e.getMessage());
        log.error("member error: {}", errors);
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * 기본 오류 처리
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandle(Exception e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("EX", "내부 오류");
    }

}
