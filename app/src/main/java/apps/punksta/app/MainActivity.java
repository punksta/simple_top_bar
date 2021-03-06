package apps.punksta.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;

import apps.punksta.openactionbar.DrawableActon;
import apps.punksta.openactionbar.IActionBar;
import apps.punksta.openactionbar.Styles;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Styles.TitleGravity titleGravity;
    private Styles.ViewType viewType;
    private TextView status;
    IActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (IActionBar) findViewById(R.id.toolbar);

        toolbar.getTitle().setText("test");

        toolbar.setActions(Arrays.asList(
                new DrawableActon(123, R.drawable.open_action_bar_menu_icon, "123"),
                new DrawableActon(123, android.R.drawable.bottom_bar, "123")
        ), false);

        titleGravity = toolbar.getTitleGravity();
        viewType = toolbar.getViewType();

        findViewById(R.id.changeGravity).setOnClickListener(this);
        findViewById(R.id.changeViewType).setOnClickListener(this);

        status = (TextView) findViewById(R.id.status);
        updateStatus();
    }

    <E extends Enum<E>> E getNext(E current) {
        E[] values = (E[]) current.getClass().getEnumConstants();
        int size = values.length;
        return values [ (current.ordinal() + 1) % size];
    }

    void updateStatus() {
        status.setText(titleGravity.toString() + " " + viewType.toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.changeGravity:
                titleGravity = getNext(titleGravity);
                toolbar.setTitleGravity(titleGravity);
                break;
            case R.id.changeViewType:
                viewType = getNext(viewType);
                toolbar.setViewType(viewType);
                break;
        }
        updateStatus();
    }
}
