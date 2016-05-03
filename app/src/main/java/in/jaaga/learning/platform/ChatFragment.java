package in.jaaga.learning.platform;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import in.jaaga.learning.R;
import in.jaaga.learning.api.Bot;
import in.jaaga.learning.api.ChatItem;
import in.jaaga.learning.api.Sender;
import in.jaaga.learning.bots.PictureBook;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChatFragmentListener} interface
 * to handle interaction events.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment implements Sender {
    public static final String USER_NAME = "freeman";

    private ChatFragmentListener mListener;

    private RecyclerView chat_view;
    private EditText chat_box;
    private static ArrayList<ChatItem> chat_list;
    private  ChatAdapter chatAdapter;
    private PictureBook testBot;

    private static Bot mBot;


    public ChatFragment() {
        if(mBot!=null)
            mBot.setSender(this);
    }

    public static ChatFragment newInstance(Object bot) {
        mBot = (Bot) bot;
        chat_list = new ArrayList<>();
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //testBot.onCreate(savedInstanceState);
        mBot.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("ChatFragment", "onCreateView");

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_chat, container, false);

        //Setup the list
        chat_view = (RecyclerView) v.findViewById(R.id.chat_view);
        chat_view.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(true);
        chat_view.setLayoutManager(linearLayoutManager);

        chatAdapter = new ChatAdapter(null,chat_list);

        chat_view.setAdapter(chatAdapter);

        chatAdapter.notifyDataSetChanged();

        //Setup the chat box
        chat_box = (EditText) v.findViewById(R.id.input_text);
        chat_box.setInputType(InputType.TYPE_CLASS_PHONE);
        chat_box.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    if (chat_box.getText().length() != 0 && chat_box.getText().toString() != "") {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(chat_box.getWindowToken(),
                                InputMethodManager.RESULT_UNCHANGED_SHOWN);

                        String text = chat_box.getText().toString();


                        ChatItem item = new ChatItem();
                        item.setMessage(text);

                        //TODO Handle name here,hardcoding for now
                        item.setSender(USER_NAME);

                        send(item);
                    }
                }
                return false;
            }
        });

        return v;
    }

    @Override
    public void onStart() {
        System.out.println("ChatFragment onStart called");
        super.onStart();
        //testBot.onStart();
        mBot.onStart();
    }

    @Override
    public void send(ChatItem item){
        chat_list.add(item);

        int position = chat_list.size()-1;
        chatAdapter.notifyItemInserted(position);
        chat_view.scrollToPosition(position);
        chat_box.setText("");
        if (item.getResponseType() == ChatItem.NUMBER_RESPONSE)
                    chat_box.setInputType(InputType.TYPE_CLASS_PHONE);
        else
            chat_box.setInputType(InputType.TYPE_CLASS_TEXT);

        if (item.getSender().equals(USER_NAME)) {
            //testBot.onMessageReceived(item.getMessage());
            mBot.onMessageReceived(item.getMessage());
        }


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ChatFragmentListener) {
            mListener = (ChatFragmentListener) context;
        } else {

        }
        mBot.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public ArrayList<ChatItem> getList(){
        System.out.println("getList:"+chat_list);
        return chat_list;
    }

    public void setList(ArrayList<ChatItem> list){
        System.out.println("setList:"+list);
        chat_list = list;
        //chatAdapter.notifyDataSetChanged();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface ChatFragmentListener {
    }
}
