'use strict';
var App = require('./src/components/App');
var ListView = require('./src/ListViewExample');
var _ = require("lodash");

var React = require('react-native');
import {LocalMediaAndroid,PlayerAndroid} from './src/Native.js';

var ReactNative = React.createClass({
  getInitialState: function() {
    return {items: [], text: '网易云'};
  },
  componentDidMount: function() {
      var self = this;
      LocalMediaAndroid.scan("/sdcard/netease/cloudmusic/Music/",function(arr){
          if(arr!==null){
              var save = _.after(arr.length,function(){
                  self.setState({items:arr});
              });
              for(var i=0;i < arr.length;i++){
                  (function(index){
                      LocalMediaAndroid.getPreview("","","","",function(img){
                          arr[index].img = img;
                          save();
                          //self.setState({items:arr});
                      });
                  })(i);
              }
          }
      });
  },
  render: function() {
    return (
      <ListView title={this.state.text} songs={this.state.items}></ListView>
    );
  }
});


React.AppRegistry.registerComponent('AwesomeProject', () => ReactNative);
