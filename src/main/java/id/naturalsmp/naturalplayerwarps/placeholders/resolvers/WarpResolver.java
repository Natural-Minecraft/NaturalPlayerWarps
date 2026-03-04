package id.naturalsmp.naturalplayerwarps.placeholders.resolvers;

import com.artillexstudios.axapi.placeholders.PlaceholderArgumentResolver;
import id.naturalsmp.naturalplayerwarps.warps.Warp;
import id.naturalsmp.naturalplayerwarps.warps.WarpManager;

import javax.annotation.Nullable;

public class WarpResolver implements PlaceholderArgumentResolver<Warp> {

    @Nullable
    @Override
    public Warp resolve(String string) {
        return WarpManager.getWarps().stream().filter(w -> w.getName().equalsIgnoreCase(string)).findAny().orElse(null);
    }
}
