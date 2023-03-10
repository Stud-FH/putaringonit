package ch.bookoflies.putaringonit.program;

import ch.bookoflies.putaringonit.account.Account;
import ch.bookoflies.putaringonit.account.Clearance;
import ch.bookoflies.putaringonit.account.AccountService;
import ch.bookoflies.putaringonit.common.ErrorResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController()
@RequestMapping("/program")
public class ProgramController {

    private final AccountService accountService;
    private final ProgramService programService;

    @PostMapping("/create")
    public ProgramResource create(
            @RequestParam("token") String token,
            @RequestBody ProgramResource data
    ) {
        Account account = this.accountService.loginWithToken(token);
        account.matchOrThrow(Clearance.Manager, ErrorResponse.Forbidden());
        Program program = this.programService.create(account.getContextName(), data);
        return new ProgramResource(program);
    }

    @PutMapping("/{id}/update")
    public ProgramResource update(
            @RequestParam("token") String token,
            @PathVariable Long id,
            @RequestBody ProgramResource data,
            @RequestParam() List<String> updates
    ) {
        this.accountService.loginWithToken(token).matchOrThrow(Clearance.Manager, ErrorResponse.Forbidden());
        Program program = this.programService.update(id, data, updates);
        return new ProgramResource(program);
    }

    @DeleteMapping("/{id}/delete")
    public void delete(
            @RequestParam("token") String token,
            @PathVariable Long id
    ) {
        this.accountService.loginWithToken(token).matchOrThrow(Clearance.Manager, ErrorResponse.Forbidden());
        this.programService.delete(id);
    }
}
