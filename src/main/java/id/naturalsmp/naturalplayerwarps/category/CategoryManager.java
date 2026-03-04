package id.naturalsmp.naturalplayerwarps.category;

import com.artillexstudios.axapi.libs.boostedyaml.block.implementation.Section;
import id.naturalsmp.naturalplayerwarps.NaturalPlayerWarps;
import id.naturalsmp.naturalplayerwarps.warps.Warp;
import id.naturalsmp.naturalplayerwarps.warps.WarpManager;

import java.util.LinkedHashMap;

import static id.naturalsmp.naturalplayerwarps.NaturalPlayerWarps.CONFIG;

public class CategoryManager {
    private static final LinkedHashMap<String, Category> categories = new LinkedHashMap<>();

    public static void reload() {
        categories.clear();

        NaturalPlayerWarps.getThreadedQueue().submit(() -> {
            if (CONFIG.getSection("categories") == null) return;
            for (String raw : CONFIG.getSection("categories").getRoutesAsStrings(false)) {
                Section section = CONFIG.getSection("categories." + raw);

                String name = section.getString("name");
                int id = NaturalPlayerWarps.getDatabase().getCategoryId(raw);

                Category category = new Category(id, raw, name, section);
                categories.put(raw, category);
            }

            for (Warp warp : WarpManager.getWarps()) {
                Category curr = warp.getCategory();
                if (curr == null) continue;
                Category nw = categories.get(curr.raw());
                warp.setCategory(nw);
            }
        });
    }

    public static LinkedHashMap<String, Category> getCategories() {
        return categories;
    }
}
