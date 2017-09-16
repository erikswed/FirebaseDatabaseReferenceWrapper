import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.your-app.android.R;
import Application;
import LogManager;
import OnFirebaseEvent
import com.serenegiant.databasereference.BaseJob;
import com.serenegiant.databasereferencert.MyDatabaseReference;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.LinkedHashMap;

/**
 * The DebugViewer show real time Firebase asynchronous activity<br>
 * This class can be removed at any time since it´s only purpose is experimental
 */
public abstract class DebugViewer extends BaseActivity {

    private final Object workItemsLock;
    private RecyclerView recyclerView;
    RecyclerViewAdapter adapter;

    public DebugViewer() {
        workItemsLock = new Object();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        adapter = new RecyclerViewAdapter(getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (LogManager.isDebugable())
            ((Button) findViewById(R.id.handle)).setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Application.getInstance().getEventBus().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Application.getInstance().getEventBus().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.onDestroy();
    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

        LinkedHashMap<Integer, BaseJob> list;
        Context context;

        public RecyclerViewAdapter(Context context) {
            this.context = context;
            list = MyDatabaseReference.getWorkItems();
            Application.getInstance().getEventBus().register(this);
        }

        @Subscribe(threadMode = ThreadMode.MAIN)
        public void onFireBase(OnFirebaseEvent workItem) {
            synchronized (workItemsLock) {
                boolean found = false;
                notifyItemChanged(workItem.baseJob.listId);
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(adapter.getItemCount());
                    }
                });

            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_work_item, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            BaseJob item = list.get(position);
            holder.setWorkItem(item, position);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

        public void onDestroy() {
            Application.getInstance().getEventBus().unregister(this);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView description;
        TextView listId;
        TextView listenerId;
        TextView listenerType;
        ImageView imageView;
        BaseJob listener;

        ViewHolder(View itemView) {
            super(itemView);
            listenerType = (TextView) itemView.findViewById(R.id.txtListenerType);
            listId = (TextView) itemView.findViewById(R.id.listId);
            description = (TextView) itemView.findViewById(R.id.description);
            listenerId = (TextView) itemView.findViewById(R.id.listenerId);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            startBlink();
        }

        public void stopBlink(){
            imageView.clearAnimation();
            imageView.setBackgroundResource(R.drawable.green_cirkel);
        }

        public void setWorkItem(BaseJob listener, int position) {
            this.description.setText(listener.description);
            this.listId.setText(Integer.toString(position));
            this.listenerId.setText(listener.id);
            listenerType.setText(listener.TAG);
            this.listener = listener;
            if(listener.stopped)
                stopBlink();
            else
                imageView.setBackgroundResource(R.drawable.red_cirkel);
        }

        private void startBlink() {
            final Animation animation = new AlphaAnimation((float) 0.5, 0); // Change alpha from fully visible to invisible
            animation.setDuration(100); // duration - half a second
            animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
            animation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
            animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in
            imageView.startAnimation(animation);
        }
    }
}
