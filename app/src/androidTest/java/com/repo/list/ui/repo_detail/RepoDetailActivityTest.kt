package com.repo.list.ui.repo_detail

import androidx.test.rule.ActivityTestRule
import com.repo.list.R
import kotlinx.android.synthetic.main.activity_repo_detail.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class RepoDetailActivityTest {

    @Rule
    @JvmField
    var activityTestRule: ActivityTestRule<RepoDetailActivity> =
        ActivityTestRule(RepoDetailActivity::class.java)

    private lateinit var activity: RepoDetailActivity

    @Before
    fun setUp() {
        activity = activityTestRule.activity
    }

    @Test
    fun onActivityReady() {
        assertNotNull(activity.tvRepoName)
        assertNotNull(activity.tvUserName)
        assertNotNull(activity.ivUserImage)
        assertNotNull(activity.tvStarTitle)
        assertNotNull(activity.tvStarCount)
        assertNotNull(activity.tvOpenIssuesTitle)
        assertNotNull(activity.tvOpenIssuesCount)

        assertEquals(activity.tvStarTitle.text,activity.getString(R.string.stars_title))
        assertEquals(activity.tvOpenIssuesTitle.text,activity.getString(R.string.open_issues_title))
    }

}