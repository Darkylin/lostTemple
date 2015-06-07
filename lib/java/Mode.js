function Mode() {
    this.modes = {};
    this.level = 0;
    this.currModeCount = 0;
    //Object.defineProperty(this, 'normal', {
    //    get: function () {
    //        return this.currModeCount == 0;
    //    }
    //})
}

Mode.prototype.enter = function (mode) {
    this.modes[mode] = true;
    this.currModeCount++;
};
Mode.prototype.quit = function (mode) {
    this.modes[mode] = null;
    this.currModeCount--;
};

Mode.prototype.is = function (name) {
    return !!this.modes[name];
};
Mode.prototype.not = function (name) {
    return !this.modes[name];
};

Mode.prototype.ifis = function (name) {//if is in this mode then quit, return in or not
    if (!!this.modes[name]) {
        this.modes[name] = false;
        return true;
    }
    return false;
};


module.exports = Mode;