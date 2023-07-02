package cli;


import org.noear.solon.sessionstate.jwt.JwtUtils;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(name = "jwt", aliases = {"j"}, description = "create jwt secret")
public class Jwt extends CommonOptions implements Callable<Integer> {


    @Override
    public Integer call() throws Exception {
        String jwtSecret = JwtUtils.createKey();
        //System.out.println(jwtSecret);
        System.out.println("--server.session.state.jwt.secret="+jwtSecret);
        return 0;
    }
}

