# Blogengine

Assignment 2 for App Development & Modeling module - A blogging engine web application with some features of blogger.com

Available at: http://remoteblogengine.herokuapp.com/

To quickly try it out, use username *a* and password *a* at this link: http://remoteblogengine.herokuapp.com/

**Development process**
- This project had 2 main phases: design and implementation
- The design phase resulted in a class diagram and some mockups and of the app
- The implementation phase was done with the Play! Framework and Test-Driven Development technique with multiple iterations 
- Each iteration is a story (use case)

**Class Diagram:**
![alt tag](https://www.dropbox.com/s/xx544x72ug7yitx/bloguml.png?raw=1)

**11 stories implemented:**
- Story 1:
User can set up an account, create many blogs, create many posts and pages in each blog and leave comments in each of those posts and pages after having logged in. 
- Story 2:
User can choose to delete his/her account
- Story 3:
Draft is supported, a draft can be switched to post and vice versa, it can be deleted if requried
- Story 4:
There are two perspectives: managing (including blog, post, page and draft) and viewing (including blog, post and page)
- Story 5:
Posts will have “id” urls while pages will have “name” urls. Posts are presented in chronological order when viewing the blog, but pages are not available
- Story 6:
User can reply to a comment and delete them if needed
- Story 7:
Third-party text editor is used for more attractive contents (ex: color, alignment, highlight, font-style, etc.)
- Story 8:
If simultaneously a post owner is editing a post and other users are viewing it, the viewers will see the change in three seconds (minimum) after the owner submit the post
- Story 9:
One user can choose to follow other people and get all the changes they have made with each of their available posts (in chronological order). All of those updates will be presented as small snapshots on the newsfeed board of the user’s home page. However, updates will disappeared if the post that they belong to is deleted. This feature is turned on by checking the “instant newsfeed” check box. And by unfollowing a user, all his/her updates will disappear.
- Story 10:
Simple and user-friendly layout with some animation, implemented with SemanticUI
- Story 11:
User can view other user's public profile

**Package Diagram**

![alt tag](https://www.dropbox.com/s/gamcl4nzef55hi1/bloguml2.jpg?raw=1)

**Demo run:**
- Landing page:

![alt tag](https://www.dropbox.com/s/cq21pa92xn7vc68/blog4.png?raw=1)

- Home page:

![alt tag](https://www.dropbox.com/s/u21rwd0q4dp3sfy/blog3.png?raw=1)

- Mangaging page:

![alt tag](https://www.dropbox.com/s/pln7dd9man5pvtx/blog1.png?raw=1)

- Making a new post:

![alt tag](https://www.dropbox.com/s/cht31g0t98ktgpr/blog2.png?raw=1)


**Some mockups before the implementation phase**
- Landing page:

![alt tag](https://www.dropbox.com/s/lzkzxm5zm7d5d9w/bloglandpage.jpg?raw=1)

- Home page:

![alt tag](https://www.dropbox.com/s/bani9xntl4kt58d/bloghomepage.jpg?raw=1)

- Mangaging page:

![alt tag](https://www.dropbox.com/s/obx4eni00kxqoox/blogmanager.jpg?raw=1)

- Profile page:

![alt tag](https://www.dropbox.com/s/wl7gp3z46prwm5j/blogprofile.jpg?raw=1)



