package at.paulhoeller.erms.presentation

data class MessageData(
    var deviceId: String,
    var message: String,
    var beacons: List<BeaconData>
)