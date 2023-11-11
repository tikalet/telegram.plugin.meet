package tikale.telegram.plugin.meet.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tikale.telegram.plugin.meet.entity.ClientSavedDto;
import tikale.telegram.plugin.meet.util.FileUtil;

@RestController
public class MeetControler {

    @Autowired
    private FileUtil fileUtil;

    @PostMapping("/save")
    public void save(@RequestBody ClientSavedDto body) {
        fileUtil.save(body.getData());
    }

    @GetMapping("/load")
    public String load() {
        return fileUtil.load();
    }
}
