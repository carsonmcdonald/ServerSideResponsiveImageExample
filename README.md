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
## License

Copyright (C) 2011 Carson McDonald

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
