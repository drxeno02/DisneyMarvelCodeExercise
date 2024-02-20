# DISNEY INTERVIEW PROJECT - ANDROID
Instructions and details for how this project was developed.

## Table of Contents

* [Build tools & versions used](#build-tools-versions-used)
* [Marvel API Keys](#marvel-api-keys)
* [Steps to run the app](#steps-to-run-app)
* [What areas of the app did you focus on?](#areas-of-focus)
* [What was the reason for your focus? What problems were you trying to solve?](#reason-for-focus-problems-solved)
* [How long did you spend on this project?](#time-spent-on-project)
* [Did you make any trade-offs for this project? What would you have done differently with more time?](#trade-offs-done-differently)
* [What do you think is the weakest part of your project?](#weakest-part-of-project)
* [Did you copy any code or dependencies? Please make sure to attribute them here!](#copy-code)
* [Is there any other information you’d like us to know?](#additional-information)
* [Resources](#resources)
* [CHANGELOG](#changelog)

<a name="build-tools-versions-used"></a>
## Build tools & versions used

Some technologies and dependencies used in this project are Material Design 3 and Material Design.
Even though we have a stable release for Material Design 3, there are still some limitations.

Ref- https://material.io/blog/material-3-compose-stable

I ended up doing a mixture, and included Compose and Coroutines.

CompileSDK = 34
MinSDK = 24
TargetSDK = 34

[table of contents](#table-of-contents)

<a name="marvel-api-keys"></a>
## Marvel API Keys

The project needs `marvel_public_api_key` and `marvel_private_api_key` to build and work. 
You can add your keys to the home level `gradle.properties` file (located in `~/.gradle` on Unix 
based systems):

```
marvelKeyPublic="<PUBLIC API KEY>"
marvelKeyPrivate="<PRIVATE API KEY>"
```

Once added to the `gradle.properties` file, your keys are synced to `build.gradle.kts` and used to
initialize the `ClientConfiguration` (@see #com.disney.framework.http.configuration.ClientConfiguration) 
in the Application() class of the project.

<a name="steps-to-run-app"></a>
## Steps to run the app

1. Add your marvel public and private API keys to `gradle.properties`.
2. Instantiate `ClientConfiguration` using your marvel public and private API keys in your application 
class, `DisneyInterviewApplication` (@see #com.disney.interview.application.DisneyInterviewApplication).

The following are required to initialize the `ClientConfiguration` instance:

| Parameter      | Type           | Field          |
| ---------------| -------------- | -------------- |
| apiKeyPublic   | String         | Required       |
| apiKeyPrivate  | String         | Required       |

Example (KOTLIN):
```
val clientConfiguration = ClientConfiguration.Builder()
    .setApiKeyPublic(BuildConfig.API_KEY_PUBLIC)
    .setApiKeyPrivate(BuildConfig.API_KEY_PRIVATE)
    .create()
```

3. Instantiate singleton instance of the `DisneyApiClientProvider` 
(@see #com.disney.framework.http.provider.DisneyApiClientProvider). The `DisneyApiClientProvider` 
provides instance provider will grant you access to the `DisneyApiClient` 
(@see #com.disney.framework.http.client.DisneyApiClient) through provided interfaces for the 
entire life of the application.

[table of contents](#table-of-contents)

<a name="areas-of-focus"></a>
## What areas of the app did you focus on?

This was my first time working with Jetpack Compose. I know there are areas of improvement with 
Compose and associated technologies such as using Hilt, or Flow. With more time, I would have liked 
to implement a ViewPager to showcase all of the retrieved comics from the endpoint, `/v1/public/comics`. 
I noticed that there were ~20 comic ids that were returned in the response. However, the instructions 
were to use 1, so I stuck to that. I figured this was for time management sake.

> Take one comic ID and display: comic book title, description and cover image

Amongst Jetpack Compose, I wanted to showcase something more robust that could indicate my ability to 
architect and design, as well as put on display my knowledge of various patterns. 
I was able to accomplish this by developing a framework that manages forming requests and 
processing responses. Through this framework I showcased a number of patterns such as Singleton, 
Builder, Structural, Adapter, Chain of Responsibility, and more.

From a system design stancepoint, this is a flexible and modular design. The framework creates a strict
contract layer to the backend APIs, enabling integrators the simplicity of making API calls without
knowing anything about the endpoints or business logic. This simplifies building apps, and can be
used to put developers on the path towards synchronization of API calls and business logic. Using
this framework could also increase speed of development of multiple projects through re-usability, 
make code maintenance easier, streamline development, and improve consistency.

[table of contents](#table-of-contents)

<a name="reason-for-focus-problems-solved"></a>
## What was the reason for your focus? What problems were you trying to solve?

An extensible framework is an infrastructure that provides ready-made components and solutions
to speed up development, efficiency and uniformity. I focused on creating a HTTP stack and gateway
because of the fact that I could showcase more complex code, while simplifying the actual work done 
in the application.

I developed a single point of access for performing requests through the `DisneyApiClient`. This 
API Client handles errors gracefully, with debugging, logging and scalability in mind. 

I used configuration to showcase that the `DisneyApiClient` could be configured and would work
in a real-world environment for any number of applications that require the same marvel requests. 
Developers referencing this framework would initialize the `ClientConfiguration` in their 
Application class.

Note: This design is scalable. For example, the inclusion of `baseUrl` to support multiple environments
could be added to the `ClientConfiguration`. There are many abstraction opportunities, for example,
sanitizing the `baseUrl` for missing protocols e.g. `http` or `https`. Such a case could handled. There
are many more use cases and possibilities.

Example (KOTLIN):
```
// initialize client configuration
val clientConfiguration = ClientConfiguration.Builder()
    .setApiKeyPublic(BuildConfig.API_KEY_PUBLIC)
    .setApiKeyPrivate(BuildConfig.API_KEY_PRIVATE)
    // allowing integrators to add a baseUrl is an exmaple of how extensible this design
    // is. With this, integrators can control different environments e.g. dev versus prod. The
    // builder allows for this flexibility.
    .setBaseUrl(baseUrl)
    .create()
```  

The `DisneyApiClientProvider` is a Singleton class that only needs to be initialized once. After
initialization the `DisneyApiClientProvider` can be accessed anytime in your application to make 
requests handled by the internal framework. The `DisneyApiClientProvider` accepts the 
`ClientConfiguration` so that you can control and manage the `DisneyApiClient`.

Example (KOTLIN):
```
// pass in context and client configuration as parameters
DisneyApiClientProvider.initialize(context, clientConfiguration)
```

Anytime you want to make requests through the `DisneyApiClient` you can retrieve an initialized
object from the `DisneyApiClientProvider` in a single line of code.

Example (KOTLIN):
```
// api client
private val apiClient: DisneyApiClientInterfaces = DisneyApiClientProvider.getInstance()
```

[table of contents](#table-of-contents)

<a name="time-spent-on-project"></a>
## How long did you spend on this project?

Below is the timesheet for the project:

Format: (yyyy-mm-dd)
1. 2024-02-19 | Framework - 2 hrs
2. 2024-02-19 | Learning Compose - 3 hrs
3. 2024-02-20 | Implementing Compose - 3.5 hrs

[table of contents](#table-of-contents)

<a name="trade-offs-done-differently"></a>
## Did you make any trade-offs for this project? What would you have done differently with more time?

The biggest trade-off for this project was trying to use Jetpack Compose. While I have reviewed PRs, 
read tutorials, and even followed developer guides about Compose, this was the first time I actually 
put it into practice. The reason why I moved forward with Compose is because this is the way forward.
This also reflects real scenarios in this fast-paced industry. At any time a decision to start using 
different technologies or architecture can be made to stay cutting edge. And then the developers have 
to learn quickly and adapt. For only having worked with Jetpack Compose for a few hours, I am happy
with my decision to showcase my flexibility and adaptability. I will only get better and more 
experienced from here. I had a good time learning and working with it!

[table of contents](#table-of-contents)

<a name="weakest-part-of-project"></a>
## What do you think is the weakest part of your project?

Compose is the weakest part of this project. I am inexperienced with it, but I am happy I was
able to get something working.

[table of contents](#table-of-contents)

<a name="copy-code"></a>
## Did you copy any code or dependencies? Please make sure to attribute them here!

No code copying. I built everything from scratch. I did have influence for Compose through
online documentation and code labs. I have referenced my resources below.

[table of contents](#table-of-contents)

<a name="additional-information"></a>
## Is there any other information you’d like us to know?

Thank you for this opportunity to interview with you. I am excited and look forward to hearing 
from you.

[table of contents](#table-of-contents)

<a name="resources"></a>
## Resources

Here are some online documentation I referred to:
1. [Compose Material 3](https://developer.android.com/jetpack/androidx/releases/compose-material3)
2. [Compose Basics](https://developer.android.com/codelabs/jetpack-compose-basics#0)
3. [Basic Layouts in Compose](https://developer.android.com/codelabs/jetpack-compose-layouts?index=..%2F..index#0)
4. [ViewModel and State in Compose](https://developer.android.com/codelabs/basic-android-kotlin-compose-viewmodel-and-state#0)

[table of contents](#table-of-contents)

<a name="changelog"></a>
## CHANGELOG

Format: (yyyy-mm-dd)
1. 2024-02-20: README created (1.0.0)\
