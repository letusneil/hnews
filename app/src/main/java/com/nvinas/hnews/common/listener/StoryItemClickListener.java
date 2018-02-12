package com.nvinas.hnews.common.listener;

import com.nvinas.hnews.data.Story;

/**
 * Created by nvinas on 11/02/2018.
 */

public interface StoryItemClickListener {

    void onItemClick(int pos, Story story);

    void onOpenStoryUrl(String url);

    void onShareStory(Story story);
}
