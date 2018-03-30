
var doSomething = function doSomeThingElse() {
    // empty function
};
var doSome = function(){};
var person = {
    get firstName() {
        return "Lingzhen";
    },
    sayName: function () {
        console.log(this.name);
    }
};
console.log(doSomething.name);
console.log(person.sayName.name);
console.log(person.firstName);