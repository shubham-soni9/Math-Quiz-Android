package com.mathgame.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

public abstract class BaseFragment extends Fragment {
    protected Context context;

    /**
     * Method to get the resource id of the Layout
     * to be displayed as the content view
     *
     * @return the resource id
     */
    protected abstract int getContentView();

    /**
     * Method to initialize all the Views in the
     * Layout of this Fragment
     *
     * @param parentView the layout of fragment
     */
    protected abstract void init(final View parentView);

    /**
     * Method to render the Data into the Layout
     *
     * @param savedInstanceState the current frament state
     */
    protected abstract void render(final Bundle savedInstanceState);


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(getContentView(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View parentView, @Nullable final Bundle savedInstanceState) {
        init(parentView);
        render(savedInstanceState);
    }


    @Override
    public void onDetach() {
        super.onDetach();

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
