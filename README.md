# MicroBean Configuration API

The MicroBean Configuration API project provides API classes and
interfaces for helping Java SE programs acquire configuration
information.

Unlike other configuration frameworks and APIs, the MicroBean
Configuration API is oriented around the concept of _configuration
coordinates_ within _configuration space_.

## Configuration Space

_Configuration space_ is defined as the unbounded universe of
configuration properties and their values, for all applications,
regardless of which system or systems might define or house them.
Dimensions within this space might include locale, region, data center
identifier, tenant identifier&mdash;these are only examples.  Of note
is that these are dimensions, not nodes within a hierarchy.  Many
configuration systems represent them as a hierarchy which makes
resolution of configuration ambiguities difficult.

## Configuration Coordinates

_Configuration coordinates_ are those which semantically locate an
application in configuration space.  So an application run in the US
locale, in the test environment, located in the Western region of a
cloud provider, in the Nevada data center and run on behalf of tenant
number 34 (to use the examples from the Configuration Space section
above) is located in configuration space with, perhaps, the following
logical configuration coordinates:

    locale=en_US
    environment=test
    region=west
    dataCenter=Nevada
    tenantId=34
    
While these coordinates look like configuration values themselves,
they are configuration values for locating the application in
configuration space, not (normally) the values that the application
will be looking up during the course of its execution.

## Configuration Properties

_Configuration properties_ are the names for which (non-hard-coded)
values are sought by an application.  `portNumber`,
`timeoutInSeconds`, `databaseUrl` and `userEmail` are arbitrary
examles of configuration properties.  These are, by definition,
non-specific identifiers.  They gain specificity only when further
pinpointed in configuration space by a set of configuration
coordinates.  That is, `databaseUrl` is a configuration property that
might have many different possible values for many different possible
applications.  The configuration property `databaseUrl` used by a
particular application is only relevant when it is interpreted in the
context of the appropriate set of configuration coordinates.  For
example, a configuration author may only be able to write a value for
the `databaseUrl` configuration property when she knows that the value
is supposed to be for the test environment in the Nevada data center
on behalf of tenant 34.  Or she may also be able to write a value for
the `databaseUrl` configuration property when she knows that the value
is supposed to be for the test environment and the Western region.  As
you can see, configuration coordinates are not hierarchical, and
neither (necessarily) are configuration properties.

## Configuration Values

_Configuration values_ are two things: the _values received by an
application_ situated in configuration space by its configuration
coordinates when that application asks for a value for a configuration
property, and _values that are written_ in such a way that they may be
found by one or more such applications.

For example, a configuration author might write a value, `red`, for a
hypothetical `color` configuration property, into a system somewhere,
once.  The author may decide (let's say) that this value is suited for
any application possessing any configuration coordinates.  In this
case, the value received by an instance of application _A_ identified
by one set of configuration coordinates, asking for a value for the
`color` configuration property will be the same as the value received
by another instance of _A_ identified by a different set of
configuration coordinates asking for a value for the `color`
configuration property.  The author thus wrote one configuration
value, but from the standpoint of the querying application instances,
there are two values in play.

## Configuration

A _configuration_ is simply a collection of some configuration
properties and their values designated for one or more
points&mdash;identified by configuration coordinates&mdash;in
configuration space.
