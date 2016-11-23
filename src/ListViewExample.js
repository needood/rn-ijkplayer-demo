/**
 * Copyright (c) 2013-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 *
 * The examples provided by Facebook are for non-commercial testing and
 * evaluation purposes only.
 *
 * Facebook reserves all rights not expressly granted.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL
 * FACEBOOK BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN
 * AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * @flow
 */
'use strict';

var React = require('react');
var ReactNative = require('react-native');
import {LocalMediaAndroid,PlayerAndroid} from './Native.js';
var {
  Image,
  ListView,
  TouchableHighlight,
  StyleSheet,
  RecyclerViewBackedScrollView,
  Navigator,
  Text,
  View,
} = ReactNative;

var ToolbarAndroid = require('ToolbarAndroid');
var UIExplorerPage = require('./UIExplorerPage');

var ListViewSimpleExample = React.createClass({
  getInitialState: function() {
    return {currentSongPath: ""};
  },
  ds:{},
  componentWillMount: function() {
    this.ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
  },
  next:function(){
      var path = this.state.currentSongPath;
      var songs = this.props.songs;
      var songsLength = songs.length;
      if(songsLength===0)return;
      var index = Math.min((Math.random()*(songsLength-1))|0,songsLength-2);
      index = songsLength===1? 0 : songs[index].path === path?songsLength-1:index;
      path = songs[index].path;
      PlayerAndroid.play(path,this.next);
      this.setState({currentSongPath:path});
  },
  play:function(path){
      PlayerAndroid.play(path,this.next);
      this.setState({currentSongPath:path});
  },
  onActionSelected: function(position) {
      if (position === 0) { // index of 'Settings'
          this.next();
      }
  },
  render: function() {
    return (
        <Navigator
            style={styles.container}
            tintColor='#FF6600'
            initialRoute={{id: 'Dashboard'}}
            renderScene={(route, navigator) =>
              <UIExplorerPage
                title={null}
                noSpacer={true}
                noScroll={true}>
                <ToolbarAndroid style={styles.toolbar}
                title={this.props.title}
                titleColor={'#FFFFFF'}
                actions={[{title: 'Play', show: 'always'}]}
                onActionSelected={this.onActionSelected}
                />
                <ListView
                  dataSource={this.ds.cloneWithRows(this.props.songs)}
                  renderRow={this._renderRow}
                  renderScrollComponent={props => <RecyclerViewBackedScrollView {...props} />}
                  renderSeparator={this._renderSeparator}
                />
              </UIExplorerPage>
            }
        />
    );
  },

  _renderRow: function(rowData: object, sectionID: string, rowID: string, highlightRow: (sectionID: string, rowID: string) => void) {
      var path =this.state.currentSongPath;
      var rowHash = Math.abs(hashCode(rowData.name));
      var imgSource = THUMB_URLS[rowHash % THUMB_URLS.length];
      var image;
      if(rowData.path === path){
          image = <Image style={styles.thumb} source={{uri:rowData.img}} />
      }
      return (
          <TouchableHighlight onPress={() => {
              var path =rowData.path;
              this.play(path);
          }}>
          <View>
          <View style={styles.row}>
          {image}
          <Text style={styles.text}>
          {rowData.name}
          </Text>
          </View>
          </View>
          </TouchableHighlight>
      );

  },

  _renderSeparator: function(sectionID: number, rowID: number, adjacentRowHighlighted: bool) {
    return (
      <View
        key={`${sectionID}-${rowID}`}
        style={{
          height:  1,
          backgroundColor: '#CCCCCC',
        }}
      />
    );
  }
});

var THUMB_URLS = [
  require('./Thumbnails/like.png'),
  require('./Thumbnails/dislike.png'),
  require('./Thumbnails/call.png'),
  require('./Thumbnails/fist.png'),
  require('./Thumbnails/bandaged.png'),
  require('./Thumbnails/flowers.png'),
  require('./Thumbnails/heart.png'),
  require('./Thumbnails/liking.png'),
  require('./Thumbnails/party.png'),
  require('./Thumbnails/poke.png'),
  require('./Thumbnails/superlike.png'),
  require('./Thumbnails/victory.png'),
  ];
/* eslint no-bitwise: 0 */
var hashCode = function(str) {
  var hash = 15;
  for (var ii = str.length - 1; ii >= 0; ii--) {
    hash = ((hash << 5) - hash) + str.charCodeAt(ii);
  }
  return hash;
};

function random(arr,path){
    var length = arr.length;
    if(length===1){
        return 0
    }else{
        var index = Math.min((Math.random()*length-1)|0,length-2);
        return arr[index].path===path?length-1:index;
    }
};
var styles = StyleSheet.create({
  row: {
    flexDirection: 'row',
    justifyContent: 'center',
    padding: 10,
    backgroundColor: '#F6F6F6',
  },
  thumb: {
    width: 64,
    height: 64,
  },
  text: {
    flex: 1,
  },
  container: {
    flex: 1,
    backgroundColor: '#F6F6EF',
  },
  titleText: {
    marginVertical: 12,
    fontSize: 20,
    color:"white",
  },
  nextContainer: {
    borderRadius: 4,
    borderWidth: 0.5,
    borderColor: '#d6d7da',
    margin: 10,
    marginTop: 0,
    height: 45,
    padding: 10,
    backgroundColor: 'white',
  },
  toolbar:{
      height: 56,
      backgroundColor: '#FF6600',
  },
  nextText: {
    fontSize: 19,
    fontWeight: '500',
  },
});

module.exports = ListViewSimpleExample;
