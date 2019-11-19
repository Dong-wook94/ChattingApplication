package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class ChattingAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<ChatlistItem> mItems;

    public ChattingAdapter(Context context, ArrayList<ChatlistItem> mItems) {
        this.mItems = mItems;
        this.inflater = (LayoutInflater)context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return mItems.size();
    }


    public ChatlistItem getItem(int position) {
        return mItems.get(position);
    }


    public long getItemId(int position) {
        return 0;
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        Context context = parent.getContext();

        /* 'listview_custom' Layout을 inflate하여 convertView 참조 획득 */
        if(convertView == null) {
            //개별 xml 불러줘야함
            convertView = inflater.inflate(R.layout.chatlist_item, parent, false);
        }
        Log.d("datasnapshot","getview 호출");
        /* 'listview_custom'에 정의된 위젯에 대한 참조 획득 */
        ImageView iv_img = (ImageView) convertView.findViewById(R.id.proImage) ;
        TextView tv_name = (TextView) convertView.findViewById(R.id.chat_name) ;
        TextView tv_contents = (TextView) convertView.findViewById(R.id.chat_content) ;

        /* 각 리스트에 뿌려줄 아이템을 받아오는데 mMyItem 재활용 */
        ChatlistItem myItem = getItem(position);

        Log.d("datasnapshot","getview position : "+position);
        Log.d("datasnapshot","getview position : "+myItem.getUserName());
        /* 각 위젯에 세팅된 아이템을 뿌려준다 */
        //iv_img.setImageDrawable(myItem.getIcon());
        tv_name.setText(myItem.getUserName());
        tv_contents.setText(myItem.getMessage());

        /* (위젯에 대한 이벤트리스너를 지정하고 싶다면 여기에 작성하면된다..)  */


        return convertView;
    }

    /* 아이템 데이터 추가를 위한 함수. 자신이 원하는대로 작성 */
    public void addItem(String name,String contents) {

        //ChatlistItem mItem = new ChatlistItem();

        /* MyItem에 아이템을 setting한다. */
        mItems.add(new ChatlistItem(name,contents));


        /* mItems에 MyItem을 추가한다. */


    }
}
