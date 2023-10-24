<img alt="Liberapay patrons" src="https://img.shields.io/liberapay/patrons/sh0inx?link=https%3A%2F%2Fen.liberapay.com%2Fsh0inx%2F"> <img alt="Modrinth Downloads" src="https://img.shields.io/modrinth/dt/dragon-cancel?label=modrinth&color=17b85a&link=https%3A%2F%2Fmodrinth.com%2Fplugin%2Fdragon-cancel"> <img alt="Spiget Downloads" src="https://img.shields.io/spiget/downloads/113210?label=spigot%20downloads&color=eb8f00&link=https%3A%2F%2Fwww.spigotmc.org%2Fresources%2Fdragon-cancel.113210%2F">

## Dragon Cancel
#### #cancelled

If you're a server owner looking to take back The End for your own purposes, nefarious or otherwise, then you've come to the right place. No more will that pesky dragon bother you or your creations in The End, or any other world you choose.

### How it works:

> Dragon Cancel takes advantage of the [NBT API](https://github.com/tr7zw/Item-NBT-API) provided by the lovely [tr7zw](https://github.com/tr7zw) to edit the ``level.dat`` file contained in any existing world folder you've specified. 

> Specifically, it changes the value of ``PreviouslyKilled`` to ``true``, so that when a player is teleported to The End for the first time, the dragon and its portal does not spawn. It will also quite literally cancel any attempts at the Enderdragon``EntitySpawnEvent`` in that world.

Really, it's that easy. 

The best part is that this will continue to work even if you remove the plugin from your server (although, it will only cancel new spawns in worlds added to the config while the plugin is loaded).

### Instructions:

1. Create the world you want to cancel the dragon in.
2. Stop the server.
3. Add the Dragon Cancel plugin to your server.
3. Add the *exact* name of the world folder to the ``config.yml`` file (you can create it, see example config below). Be sure to add quotes around it, and if you're cancelling multiple worlds, separate each entry with a comma.
4. Start your server.

### Caveats:

- The plugin will work on any worlds that are in the root server folder. However, it will not work on worlds that are created after the server is started.
- The world that you want to cancel the dragon in MUST be created and exist first. It cannot create the ``level.dat`` for you.

### Example:

> ``config.yml`` in ``plugins/dragonCancel``
```yaml
worlds: ["world_the_end", "spawn"]
```
- ``world_the_end``: The End, where the dragon naturally no longer spawns.
- ``spawn``: The Overworld, for if you don't want a dragon to be able to spawn there (for whatever reason).
