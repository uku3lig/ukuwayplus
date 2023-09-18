> [!IMPORTANT]
> Hideaway+ has ceased service, all code exists as an archive. If you would like the Github or Twitter handles please contact @imskylerwhiteyo on Discord or @servantofsappho on Twitter.
> Feel free to use this code in your project - credit is appreciated but not mandated. I look forward to seeing all the Hideaway code projects that come in future! ❤️ - Skye

## 🍍 Hideaway+ - a QOL mod for Hideaway

### Navigating the repository
My code is an absolute mess, so here are some pointers:
- `src/main/java` - all code for the mod
  - `plus.hideaway.mod` - main mod class (and a class I forgot to move into the utils package)
  - `plus.hideaway.feat` - core features or services provided by the mod:
    - `plus.hideaway.feat.config` - config/settings provider
    - `plus.hideaway.feat.discord` - Discord RPC (presence) provider
    - `plus.hideaway.feat.jukebox` - "Jukebox" service provider which could play any track from the Hideaway OST anywhere, loop it, and a few other things. Slightly buggy and needs more specific track lengths to avoid silence but a definite proof of concept
    - `plus.hideaway.feat.keyboard` - keybinds provider
    - `plus.hideaway.feat.lifecycle` - simple implementation of a task scheduler to run simple repeating or delayed tasks
    - `plus.hideaway.feat.location` - location provider for Discord RPC and other services
    - `plus.hideaway.feat.ui` - unfinished migration to a new UI library, currently GUIs do not function. Previously used Fabric's Screen API to provide GUIs but we wanted a more custom feel. We were intending to move back as OwoUI proved to be a thorn in our side as its docs were a little vague and didn't seem to line up with observed results when trying in-game
  - `plus.hideaway.mixins` - all mixins
    -`plus.hideaway.mixins.accessors` - two accessor classes for a hacky fix to allow me to inject a button into the pause menu. There's almost certainly an easier method but it was 3am
  - `plus.hideaway.util` - utility classes, as well as constants for custom font characters provided by the mod
- `src/main/resources` - resource pack and Fabric config files
- `/assets` - assets not used in the project which were put on social media accounts, or used for Discord RPC images
