package apps.punksta.openactionbar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by punksta on 15.01.16.
 */
class ActionBuilder {
    private static final View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            Action action = (Action) v.getTag();
            if (action != null) {
                Toast.makeText(v.getContext(), action.getName(), Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        }
    };

    public static List<View> fillLayout(final Context context, List<? extends Action> buttons, ViewGroup group) {
        List<View> result = new ArrayList<>(buttons.size());
        for (final Action barButton : buttons) {
            View v = fromAbstract(barButton, context, group);

            result.add(v);
            group.addView(v);
            ViewGroup.LayoutParams params = v.getLayoutParams();
        }
        return result;
    }

    private static View fromAbstract(Action action, Context c, ViewGroup group) {
        View view;
        if (action instanceof CustomViewAction)
            view = fromCustom((CustomViewAction) action, c, group);
        else if (action instanceof DrawableActon)
            view = fromDrawable((DrawableActon) action, c);
        else
            return null;

        view.setTag(action);
        view.setId(action.getId());
        view.setOnLongClickListener(onLongClickListener);
        return view;
    }

    private static View fromCustom(CustomViewAction action, Context context,  ViewGroup group) {
        return LayoutInflater.from(context).inflate(action.getViewRes(), group, false);
    }

    private static View fromDrawable(DrawableActon action, Context context) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(action.getDrawable());
        int size = (int) context.getResources().getDimension(R.dimen.open_action_bar_action_button_size);
        int padding = (int) context.getResources().getDimension(R.dimen.open_action_bar_action_button_padding);
        imageView.setPadding(padding, padding, padding, padding);
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(size, size);
        imageView.setLayoutParams(params);
        return imageView;
    }
}
