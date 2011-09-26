Server Side Responsive Images
=============================

This is an example of using device detection to create server side responsive images.

The device detection is provided by the [WURFL](http://wurfl.sourceforge.net/) database and [Java library](http://wurfl.sourceforge.net/njava/). The images are scaled based on the device height and width.

## Setup

The project is set up to run using Heroku or localy by executing the ResponsiveImage class. Before deploying you will need to download the latest WURFL database from http://sourceforge.net/projects/wurfl/files/WURFL/2.2/ and put it here src/main/webapp/WEB-INF/wurfl.zip

## Example

A test page is included in the source to demo the service. You can find it at the root of the site.

The following is an example of how to use the service once it is running:

``` html
   <img src="/rimage/?src=http://farm3.static.flickr.com/2782/4455976588_936f517e16_o.jpg"/> 
```

