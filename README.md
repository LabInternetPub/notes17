# HATEOAS: Hypermedia as the Engine of Application State

## Definition
This first introduction is taken from https://en.wikipedia.org/wiki/HATEOAS

HATEOAS is a constraint of the REST application architecture that distinguishes it from other network application architectures.

With HATEOAS, a client interacts with a network application that application servers provide dynamically entirely through hypermedia. 
A REST client needs no prior knowledge about how to interact with an application or server beyond a generic understanding of hypermedia.
The way that the HATEOAS constraint decouples client and server enables the server functionality to evolve independently.

A REST client enters a REST application through a simple fixed URL. All future actions the client may take are discovered within 
resource representations returned from the server. The media types used for these representations, and the link relations they 
may contain, are standardized. The client transitions through application states by selecting from the links within a representation 
or by manipulating the representation in other ways afforded by its media type. In this way, RESTful interaction is driven by hypermedia, 
rather than out-of-band information.

That is to say, the resources come along with additional information/relations that the client can scan in order to discover 
what else it can do. Ideally knowing one single URL the client should be able to fully operate with the REST API.

**Example:**
With the call *http://localhost:8080/api/users* it gets a list with all users and users contain information on how to access
them individually and how is it possible to get their notes

```javascript
[
    {
        "username": "roure",
        "name": "Josep",
        "secondName": "Roure",
        "email": "roure@tecnocampus.cat",
        "password": "$2a$10$0VGzG8lfiDXBnFTE98lfiOLtP4uh62wnE6iWs5.2AMrJ3G9k7XZqu",
        "enabled": true,
        "notes": [],
        "links": [
            {
                "rel": "self",
                "href": "http://localhost:8080/api/users/roure"
            },
            {
                "rel": "notes",
                "href": "http://localhost:8080/api/users/roure/notes"
            }
        ]
    },
    {
        "username": "alvarez",
        "name": "Sergi",
        "secondName": "Alvarez",
        "email": "alvarez@mail.cat",
        "password": "$2a$10$7PFxXn4TQRiut9jNcAl7AubQZUWWck/eML3TDaQtoZiWNEN6o.Ig6",
        "enabled": true,
        "notes": [],
        "links": [
            {
                "rel": "self",
                "href": "http://localhost:8080/api/users/alvarez"
            },
            {
                "rel": "notes",
                "href": "http://localhost:8080/api/users/alvarez/notes"
            }
        ]
    }
]
```

Then the client can for example obtain user roure: *http://localhost:8080/api/users/roure/notes*

```javascript
{
    "username": "roure",
    "name": "Josep",
    "secondName": "Roure",
    "email": "roure@tecnocampus.cat",
    "password": "$2a$10$0VGzG8lfiDXBnFTE98lfiOLtP4uh62wnE6iWs5.2AMrJ3G9k7XZqu",
    "enabled": true,
    "notes": [
        {
            "title": "spring",
            "content": "va super be",
            "dateCreation": [
                2017,
                10,
                27,
                13,
                35,
                36,
                177000000
            ],
            "dateEdit": "2017-10-27 13:35:36",
            "_links": {
                "self": {
                    "href": "http://localhost:8080/api/users/roure/notes/0"
                }
            }
        },
        {
            "title": "spring boot",
            "content": "va encara millor",
            "dateCreation": [
                2017,
                10,
                27,
                13,
                35,
                36,
                177000000
            ],
            "dateEdit": "2017-10-27 13:35:36",
            "_links": {
                "self": {
                    "href": "http://localhost:8080/api/users/roure/notes/1"
                }
            }
        }
    ],
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/users/roure"
        },
        "notes": {
            "href": "http://localhost:8080/api/users/roure/notes"
        }
    }
}
```

## Our Spring example
Spring provides the class *ResourceSupport* from where to extend and easily add HATEOAS links. I wanted to keep our domain
classes (UserLab, NoteLab)free from coupling with Spring. So, I created a package called *hateoasResources* to define a 
NoteLabResource and UserLabResource.

Note that NoteLabResource composes a NoteLab and provides the corresponding note attribute (via method *getNotes()*). See also that
this attribute is annotated with **@JsonUnwrapped**. With this annotation we avoid obtaining a resource where
