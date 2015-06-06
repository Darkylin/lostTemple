function compile(path, callback, options, outputType) {
    if (outputType == "java") {
        return require('./lib/java/compiler')(path, callback, options);
    } else if (outputTpe === undefined || outputType == "javascript") {
        return require('./lib/javascript/compiler')(path, callback, options);
    } else {
        throw new Error('outputType could only be "java" or "javascript"');
    }
}

exports.compile = compile

compile('a/b/test.hbs', function () {
}, {
    cwd: 'test/resources'
}, 'java');
