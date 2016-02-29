package in.jaaga.learning.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import in.jaaga.learning.InteractionInterface;
import in.jaaga.learning.LearningContext;
import in.jaaga.learning.MissionLibrary;
import in.jaaga.learning.R;
import in.jaaga.learning.Session;
import in.jaaga.learning.android.AndroidDB;
import in.jaaga.learning.android.AndroidLanguageMission;
import in.jaaga.learning.android.AndroidMathMission;
import in.jaaga.learning.android.S;
import in.jaaga.learning.adapter.ChatAdapter;
import in.jaaga.learning.Learning;
import in.jaaga.learning.ChatItem;
import in.jaaga.learning.android.AndroidChatBot;
import in.jaaga.learning.missions.MathMission;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChatFragmentListener} interface
 * to handle interaction events.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment implements InteractionInterface{
    private ChatFragmentListener mListener;

    private RecyclerView chat_view;
    private EditText chat_box;
    private static ArrayList<ChatItem> chat_list;
    private ChatAdapter chatAdapter;
    private Learning learning;

    private static ChatFragment currentInstance;





    public ChatFragment() {
        MissionLibrary ml = new MissionLibrary();
        ml.addMission("math", new AndroidMathMission());
        ml.addMission("vocab", new AndroidLanguageMission());
        Session session = new Session();
        LearningContext learningContext = new LearningContext(this, session, new AndroidChatBot(session),
                ml, new AndroidDB());
        learning = new Learning(learningContext);
        learning.setMission(new MathMission());
        currentInstance = this;
    }

    public static ChatFragment newInstance() {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        chat_list = new ArrayList<>();
        return fragment;
    }

    public static ChatFragment getCurrentInstance(){
        return currentInstance;
    }

    public Learning getLearning(){
        return learning;
    }

    public void setLearning(Learning learning){
        this.learning = learning;
    }

@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        S.init(getResources(), getActivity());

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_chat, container, false);

        //Setup the list
        chat_view = (RecyclerView) v.findViewById(R.id.chat_view);
        chat_view.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(true);
        chat_view.setLayoutManager(linearLayoutManager);
        chatAdapter = new ChatAdapter(getActivity(),chat_list);
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
                        item.setSender("amar");

                        send(item);
                        learning.onResponse(text);
                    }
                }
                return false;
            }
        });

        if (chat_list.isEmpty())
            learning.start();

        return v;
    }

    @Override
    public void send(ChatItem item){
        chat_list.add(item);

        int position = chat_list.size()-1;
        chatAdapter.notifyItemInserted(position);
        chat_view.scrollToPosition(position);
        chat_box.setText("");
        if (item.getResponseType() == Learning.NUMBER_RESPONSE)
            chat_box.setInputType(InputType.TYPE_CLASS_PHONE);
        else
            chat_box.setInputType(InputType.TYPE_CLASS_TEXT);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ChatFragmentListener) {
            mListener = (ChatFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ChatFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public ArrayList<ChatItem> getList(){
        return chat_list;
    }

    public void setList(ArrayList<ChatItem> list){

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
