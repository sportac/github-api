

# Github API

Android app that connects with Github API and retrieves the authenticated user repositories and
allows to check the list of commits for each repository.

The frontend has been shaped as a native Android app built in java, making use of few libraries to 
help with connectivity and app usability.

# Tech Behind
## Github API Connectivity

Rest API that allows to create integrations and retrieve data for a user or organisation with a Github 
account.

**Authentication:** In this case, the authentication has been done using a personal access token, 
particularly, by generating a Fine-grained personal access token from the Github Developer settings.

To authenticate the requests, the token is passed as an Authorisation header in each request:

```jsx
curl --request GET \
--url "https://api.github.com/octocat" \
--header "Authorization: Bearer YOUR-TOKEN"
```

**Requests**

**[GET Repositories](https://docs.github.com/en/rest/repos/repos?apiVersion=2022-11-28#list-repositories-for-the-authenticated-user)  →** Request used to retrieve all the repositories for a given user.

**[GET Commits](https://docs.github.com/en/rest/commits/commits?apiVersion=2022-11-28)** → Request used to retrieve the commits in a repository. The “page” and ”page_num” query parameters have been used to progressively retrieve the commits as the user scrolls down.

## App Architecture

MVVM design pattern has been used to build the foundations of the mobile app.

1. **Models** → Models have been shaped to map the response from the Github REST API
    - **Repository**: It handles the data and logic related to repositories. It communicates with the Github API to retrieve the repositories for the authenticated user.
    - **Commit**: It handles the data and logic related to commits. It communicates with the Github API to retrieve the commits for a given repository.
2. **ViewModel**:
    - **GithubApiViewModel**: It acts as an intermediary between the Model and the View. It retrieves the data from the **Github Repository** and the Models and transforms it into a format that the View can use.
3. **View**:
    - **MainActivity (Repositories)**: It displays the list of repositories retrieved by the RepositoriesViewModel. It also handles user input, such as clicking on a repository to navigate to the Commits view.
    - **CommitsActivity**: It displays the list of commits retrieved by the CommitsViewModel. It also handles user input, such as scrolling down to retrieve more commits.

Overall, the Model classes represent the data and the API interactions, the View classes represent the user interface, and the ViewModel classes act as intermediaries between the Model and View, handling the data and logic to be displayed in the View. By separating the modules of the application in this way, the MVVM pattern allows for better organisation, maintainability and testability of the code.


## Design

The frontend design of the Github web interface has been used as reference to build the app. Similar font and same colour palette has been used to give the user the feeling of using an official app developed by Github.

## Libraries

- Retrofit: http client for Android and Java.
- RxJava: Manage data and events across the application.
- Swiperefreshlayout: to support pull to refresh and enhanced scrolling capabilities.
- Glide: Images loading and cache manager.

## Highlighted Functionalities

- Utils method to convert from timestamp to text of the type “Update 4 weeks ago”. By parsing the timestamp retrieved from Github and comparing it with the current time and displaying the difference accordingly by defining thresholds of minutes, hours, days, weeks, months and years.
- Utils method to assign label colors to the different programming languages, following the same patters used by Github official website.
- Commits Activity pagination. Given a window size of 10 commits, it detects if the user is scrolling down and progressively sends more requests to retrieve more commits in chunks of 10. Once there are no more commits a flag is set to stop keep sending requests to the API. Also, if the user performs a pull to refresh action all data is restored to initial value.


## Potential improvements
- Create a login page
- Store Github token in environment file
- Authenticate with OAuth - define scopes and retrieve token to be stored locally.
- Allow filter commits by branch
- Allow to sort commits by timestamp or define time range.