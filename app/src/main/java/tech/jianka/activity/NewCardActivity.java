package tech.jianka.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import tech.jianka.data.Item;

import static tech.jianka.utils.SDCardHelper.Obj2Bytes;
import static tech.jianka.utils.SDCardHelper.saveFileToSDCard;

public class NewCardActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private RadioGroup mTaskSelecotor;
    private EditText mEditTitle;
    private Spinner mGroupSelector;
    private EditText mEditContent;
    private TextView mIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);

        setTitle(getString(R.string.new_card_activity));
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mIndicator = (TextView) findViewById(R.id.new_card_task_indicator);

        mEditTitle = (EditText) findViewById(R.id.new_card_title);
        mEditContent = (EditText) findViewById(R.id.new_card_content);
        mTaskSelecotor = (RadioGroup) findViewById(R.id.new_card_task_selector);
        mGroupSelector = (Spinner) findViewById(R.id.new_card_group_selector);
        mEditContent = (EditText) findViewById(R.id.new_card_content);
        mTaskSelecotor.setOnCheckedChangeListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_card,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_save:
                saveCard();
                finish();
            case R.id.action_insert_image:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveCard() {
        String title = mEditTitle.getText().toString();
        String content = mEditContent.getText().toString();
        Item newCard = new Item();
        newCard.setItemType(Item.CARD);
        newCard.setCardTitle(title);
        newCard.setCardContent(content);
        if(mIndicator.getText().toString()=="常规"){
            saveFileToSDCard(Obj2Bytes(newCard), "jianka/data/"+mGroupSelector.getSelectedItem().toString(), title + ".card");
        }else{
            saveFileToSDCard(Obj2Bytes(newCard), "jianka/task/"+mIndicator.getText().toString(), title + ".card");
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        if (group.getId() == R.id.new_card_task_selector) {
            String[] tasks = getResources().getStringArray(R.array.task);
            switch (checkedId) {
                case R.id.task_regular:
                    mIndicator.setText(tasks[0]);
                    break;
                case R.id.task_important_emergent:
                    mIndicator.setText(tasks[1]);
                    break;
                case R.id.task_important_not_emergent:
                    mIndicator.setText(tasks[2]);
                    break;
                case R.id.task_unimportant_emergent:
                    mIndicator.setText(tasks[3]);
                    break;
                case R.id.task_unimportant_not_emergent:
                    mIndicator.setText(tasks[4]);
                    break;
            }
        }
    }
//    StringBuffer status = new StringBuffer();
//    //①获取系统的Configuration对象
//    Configuration cfg = getResources().getConfiguration();
//    RecyclerViewBanner recyclerViewBanner1 = (RecyclerViewBanner) findViewById(R.id.rv_banner_1);
//
//    //②想查什么查什么
//        status.append("densityDpi:" + cfg.densityDpi + "\n");
//        status.append("fontScale:" + cfg.fontScale + "\n");
//        status.append("hardKeyboardHidden:" + cfg.hardKeyboardHidden + "\n");
//        status.append("keyboard:" + cfg.keyboard + "\n");
//        status.append("keyboardHidden:" + cfg.keyboardHidden + "\n");
//        status.append("locale:" + cfg.locale + "\n");
//        status.append("mcc:" + cfg.mcc + "\n");
//        status.append("mnc:" + cfg.mnc + "\n");
//        status.append("navigation:" + cfg.navigation + "\n");
//        status.append("navigationHidden:" + cfg.navigationHidden + "\n");
//        status.append("orientation:" + cfg.orientation + "\n");
//        status.append("screenHeightDp:" + cfg.screenHeightDp + "\n");
//        status.append("screenWidthDp:" + cfg.screenWidthDp + "\n");
//        status.append("screenLayout:" + cfg.screenLayout + "\n");
//        status.append("smallestScreenWidthDp:" + cfg.densityDpi + "\n");
//        status.append("touchscreen:" + cfg.densityDpi + "\n");
//        status.append("uiMode:" + cfg.densityDpi + "\n");
//        txtResult.setText(status.toString());

}