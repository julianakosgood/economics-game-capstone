# economics-game-capstone

https://gitlab.com/capstone-economics-game/economics-game/-/wikis/Testing

# Unit Tests

* test_[data]_transform: tests that validate a mock data set(as received from data sources) is properly transformed.
* test_time_step_mutation: tests that ensure a "turn" in the game properly mutates target datasets.
* test_player_assignments: tests that ensure functions related to assigning players to game instances properly function.
* test_purchase: tests for functions related to player "purchases" in game
* test_[ui_component]_render: tests to validate a component renders expected output

# System tests

* test_start_game: test to ensure a game instance is properly instantiated and populated with appropriate data.
* test_instance_relationships: test to ensure players are assigned, filtered, and viewable by the game admin.
* test_data_population: test to ensure the data is properly retrieved, transformed, projected, and assignable for a game instance.
* test_admin_view: test to ensure expected admin functions and UI components are working as expected.
* test_player_view: as above, but for a player view.
* test_game_time: test to ensure a game timeline runs properly, with all expected effects.

# Acceptance tests

* Player may see and participate in an assigned game instance. Where participation includes: buying "stock", receiving money, viewing various UI components.
* Admin may start a game, assign players, create accounts, increment game time lines, and access defined UI components and management tools.
