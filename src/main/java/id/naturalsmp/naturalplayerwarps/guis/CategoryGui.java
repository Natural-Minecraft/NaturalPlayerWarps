package id.naturalsmp.naturalplayerwarps.guis;

import com.artillexstudios.axapi.config.Config;
import com.artillexstudios.axapi.libs.boostedyaml.settings.dumper.DumperSettings;
import com.artillexstudios.axapi.libs.boostedyaml.settings.general.GeneralSettings;
import com.artillexstudios.axapi.libs.boostedyaml.settings.loader.LoaderSettings;
import com.artillexstudios.axapi.libs.boostedyaml.settings.updater.UpdaterSettings;
import com.artillexstudios.axapi.utils.StringUtils;
import com.artillexstudios.axguiframework.GuiFrame;
import id.naturalsmp.naturalplayerwarps.NaturalPlayerWarps;
import id.naturalsmp.naturalplayerwarps.user.Users;
import id.naturalsmp.naturalplayerwarps.user.WarpUser;
import com.artillexstudios.gui.guis.Gui;
import org.bukkit.entity.Player;

import java.io.File;

public class CategoryGui extends GuiFrame {
    private static final Config GUI = new Config(new File(NaturalPlayerWarps.getInstance().getDataFolder(), "guis/categories.yml"),
            NaturalPlayerWarps.getInstance().getResource("guis/categories.yml"),
            GeneralSettings.builder().setUseDefaults(false).build(),
            LoaderSettings.builder().build(),
            DumperSettings.DEFAULT,
            UpdaterSettings.builder().build()
    );

    private final Gui gui = Gui
            .gui()
            .disableAllInteractions()
            .title(StringUtils.format(GUI.getString("title", "---")))
            .rows(GUI.getInt("rows", 5))
            .create();
    private final WarpUser user;

    public CategoryGui(Player player) {
        super(GUI.getInt("auto-update-ticks", -1), GUI, player);
        this.user = Users.get(player);

        setGui(gui);
        user.addGui(this);
    }

    public static boolean reload() {
        return GUI.reload();
    }

    public void open() {
        gui.update();
        gui.open(player);
    }
}
