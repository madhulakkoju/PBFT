// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: linearpbft.proto

package org.cse535.proto;

public interface TransactionOrBuilder extends
    // @@protoc_insertion_point(interface_extends:Transaction)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string sender = 1;</code>
   */
  java.lang.String getSender();
  /**
   * <code>string sender = 1;</code>
   */
  com.google.protobuf.ByteString
      getSenderBytes();

  /**
   * <code>string receiver = 2;</code>
   */
  java.lang.String getReceiver();
  /**
   * <code>string receiver = 2;</code>
   */
  com.google.protobuf.ByteString
      getReceiverBytes();

  /**
   * <code>int32 amount = 3;</code>
   */
  int getAmount();

  /**
   * <code>.google.protobuf.Timestamp timestamp = 4;</code>
   */
  boolean hasTimestamp();
  /**
   * <code>.google.protobuf.Timestamp timestamp = 4;</code>
   */
  com.google.protobuf.Timestamp getTimestamp();
  /**
   * <code>.google.protobuf.Timestamp timestamp = 4;</code>
   */
  com.google.protobuf.TimestampOrBuilder getTimestampOrBuilder();

  /**
   * <code>string transactionHash = 5;</code>
   */
  java.lang.String getTransactionHash();
  /**
   * <code>string transactionHash = 5;</code>
   */
  com.google.protobuf.ByteString
      getTransactionHashBytes();

  /**
   * <code>int32 transactionNum = 6;</code>
   */
  int getTransactionNum();
}
