# Production Release Checklist

Before making the repository public:

- [ ] FIRST manufacturer ID assigned to Pennington Laboratories.
- [ ] CAN device type confirmed as Distance Sensor (`6`).
- [ ] CAN API class/index map frozen.
- [ ] CAN packet byte layouts frozen.
- [ ] Firmware and library tested together on physical LARS hardware.
- [ ] FRC robot integration test completed.
- [ ] Java library tested on roboRIO.
- [ ] C++ library completed and tested if included in vendor release.
- [ ] Maven repository published.
- [ ] Vendor JSON points to the real Maven repository.
- [ ] Vendor JSON installs successfully through WPILib VS Code.
- [ ] README installation instructions verified on a clean FRC project.
- [ ] No firmware, credentials, private PCB files, or internal documents are present.
- [ ] Version/tag created.
- [ ] GitHub release published.
