var Dispatcher = require("./Dispatcher");
var Events = require("events");
var _ = require("underscore");

var EventEmitter = Events.EventEmitter;

var data = [];

Dispatcher.register(function(action) {
	var text;

	switch (action.actionType) {
		case "addTodo":
			TodoStore.add(action.text);
			break;
		case "updateTodo":
			TodoStore.update(action.index,action.text);
			break;
		case "toggleDoneTodo":
			TodoStore.toggleDone(action.index);
			break;
		case "deleteTodo":
			TodoStore.delete(action.index);
			break;
		case "deleteAll":
			TodoStore.deleteAll();
			break;
		case "clear":
			TodoStore.clear();
			break;
	}
});

var TodoStore = _.extend({
	add: function(text) {
		data.push({text,done:false});
		this.emit("ListChanged");
	},
	update:function(index,text){
		data[index].text = text;
		this.emit("ListChanged");
	},
	toggleDone:function(index){
		data[index].done = !data[index].done;
		this.emit("ListChanged");
	},
	delete:function(index){
		data.splice(index, 1);
		this.emit("ListChanged");
	},
	clear:function(){
        for(var i=data.length-1;i>=0;i--){
            if(data[i].done){
                data.splice(i, 1);
            }
        }
		this.emit("ListChanged");
	},
	deleteAll:function(){
		data = [];
		this.emit("ListChanged");
	},
	get:function(){
		return data;
	},
	addChangeListener: function(callback) {
		this.on("ListChanged", callback);
	},
	removeChangeListener: function(callback) {
		this.removeListener("ListChanged", callback);
	}
},EventEmitter.prototype);

module.exports = TodoStore;
