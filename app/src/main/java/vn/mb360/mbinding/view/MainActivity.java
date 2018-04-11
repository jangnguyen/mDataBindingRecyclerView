package vn.mb360.mbinding.view;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.mb360.mbinding.R;
import vn.mb360.mbinding.databinding.ActivityMainBinding;
import vn.mb360.mbinding.model.Post;
import vn.mb360.mbinding.model.User;
import vn.mb360.mbinding.utils.GridSpacingItemDecoration;

public class MainActivity extends AppCompatActivity implements PostsAdapterListener {

    private MyClickHandlers handlers;
    private PostsAdapter mAdapter;
    private RecyclerView recyclerView;
    private User user;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        handlers = new MyClickHandlers(this);

        initRecyclerView();
        renderProfile();
    }

    private void renderProfile() {
        user = new User();
        user.setName("Nguyễn Trường Giang");
        user.setEmail("giangnguyen@gmail.com");
        user.setProfileImage("https://scontent.fsgn5-6.fna.fbcdn.net/v/t1.0-9/29136775_1701754799845218_2907644854542532608_n.jpg?_nc_cat=0&oh=e40d211f9a8d46bf1c928a94d9f81022&oe=5B2A8749");
        user.setAbout("Naturalist");
        user.numberOfPosts.set(3900L);
        user.numberOfFollowers.set(3050890L);
        user.numberOfFollowing.set(196L);

        binding.setUser(user);
        binding.content.setHandlers(handlers);
    }

    private void initRecyclerView() {
        recyclerView = binding.content.recyclerView;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(4), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        mAdapter = new PostsAdapter(getPost(), this);
        recyclerView.setAdapter(mAdapter);
    }

    private List<Post> getPost() {
        ArrayList<Post> posts = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            Post post = new Post();
            post.setImageUrl("https://api.androidhive.info/images/nature/" + i + ".jpg");
            posts.add(post);
        }
        return posts;
    }

    @Override
    public void onPostClicked(Post post) {
        Toast.makeText(getApplicationContext(), "Post clicked! " + post.getImageUrl(), Toast.LENGTH_SHORT).show();
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public class MyClickHandlers {
        Context context;

        public MyClickHandlers(Context context) {
            this.context = context;
        }

        public void onProfileFabClicked(View view) {
            user.setName("Mr. Nguyễn Trường Giang");
            user.setProfileImage("https://scontent.fsgn5-6.fna.fbcdn.net/v/t1.0-9/29136775_1701754799845218_2907644854542532608_n.jpg?_nc_cat=0&oh=e40d211f9a8d46bf1c928a94d9f81022&oe=5B2A8749");
            user.numberOfPosts.set(6970L);
            user.numberOfFollowers.set(7943607L);
            user.numberOfFollowing.set(297L);
        }

        public boolean onProfileImageLongPressed(View view) {
            Toast.makeText(getApplicationContext(), "Profile image long pressed!", Toast.LENGTH_SHORT).show();
            return false;
        }

        public void onFollowersClicked(View view) {
            Toast.makeText(context, "Followers is clicked!", Toast.LENGTH_SHORT).show();
        }

        public void onFollowingClicked(View view) {
            Toast.makeText(context, "Following is clicked!", Toast.LENGTH_SHORT).show();
        }

        public void onPostsClicked(View view) {
            Toast.makeText(context, "Posts is clicked!", Toast.LENGTH_SHORT).show();
        }
    }
}
