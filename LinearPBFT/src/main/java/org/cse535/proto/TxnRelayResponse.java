// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: linearpbft.proto

package org.cse535.proto;

/**
 * Protobuf type {@code TxnRelayResponse}
 */
public  final class TxnRelayResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:TxnRelayResponse)
    TxnRelayResponseOrBuilder {
private static final long serialVersionUID = 0L;
  // Use TxnRelayResponse.newBuilder() to construct.
  private TxnRelayResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private TxnRelayResponse() {
    success_ = false;
    serverName_ = "";
    option_ = 0;
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private TxnRelayResponse(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 8: {

            success_ = input.readBool();
            break;
          }
          case 18: {
            java.lang.String s = input.readStringRequireUtf8();

            serverName_ = s;
            break;
          }
          case 24: {

            option_ = input.readInt32();
            break;
          }
          case 34: {
            org.cse535.proto.ExecutionReplyRequest.Builder subBuilder = null;
            if (executionReply_ != null) {
              subBuilder = executionReply_.toBuilder();
            }
            executionReply_ = input.readMessage(org.cse535.proto.ExecutionReplyRequest.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(executionReply_);
              executionReply_ = subBuilder.buildPartial();
            }

            break;
          }
          default: {
            if (!parseUnknownFieldProto3(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return org.cse535.proto.Linearpbft.internal_static_TxnRelayResponse_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return org.cse535.proto.Linearpbft.internal_static_TxnRelayResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            org.cse535.proto.TxnRelayResponse.class, org.cse535.proto.TxnRelayResponse.Builder.class);
  }

  public static final int SUCCESS_FIELD_NUMBER = 1;
  private boolean success_;
  /**
   * <code>bool success = 1;</code>
   */
  public boolean getSuccess() {
    return success_;
  }

  public static final int SERVERNAME_FIELD_NUMBER = 2;
  private volatile java.lang.Object serverName_;
  /**
   * <code>string serverName = 2;</code>
   */
  public java.lang.String getServerName() {
    java.lang.Object ref = serverName_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      serverName_ = s;
      return s;
    }
  }
  /**
   * <code>string serverName = 2;</code>
   */
  public com.google.protobuf.ByteString
      getServerNameBytes() {
    java.lang.Object ref = serverName_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      serverName_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int OPTION_FIELD_NUMBER = 3;
  private int option_;
  /**
   * <code>int32 option = 3;</code>
   */
  public int getOption() {
    return option_;
  }

  public static final int EXECUTIONREPLY_FIELD_NUMBER = 4;
  private org.cse535.proto.ExecutionReplyRequest executionReply_;
  /**
   * <code>.ExecutionReplyRequest executionReply = 4;</code>
   */
  public boolean hasExecutionReply() {
    return executionReply_ != null;
  }
  /**
   * <code>.ExecutionReplyRequest executionReply = 4;</code>
   */
  public org.cse535.proto.ExecutionReplyRequest getExecutionReply() {
    return executionReply_ == null ? org.cse535.proto.ExecutionReplyRequest.getDefaultInstance() : executionReply_;
  }
  /**
   * <code>.ExecutionReplyRequest executionReply = 4;</code>
   */
  public org.cse535.proto.ExecutionReplyRequestOrBuilder getExecutionReplyOrBuilder() {
    return getExecutionReply();
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (success_ != false) {
      output.writeBool(1, success_);
    }
    if (!getServerNameBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, serverName_);
    }
    if (option_ != 0) {
      output.writeInt32(3, option_);
    }
    if (executionReply_ != null) {
      output.writeMessage(4, getExecutionReply());
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (success_ != false) {
      size += com.google.protobuf.CodedOutputStream
        .computeBoolSize(1, success_);
    }
    if (!getServerNameBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, serverName_);
    }
    if (option_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(3, option_);
    }
    if (executionReply_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(4, getExecutionReply());
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof org.cse535.proto.TxnRelayResponse)) {
      return super.equals(obj);
    }
    org.cse535.proto.TxnRelayResponse other = (org.cse535.proto.TxnRelayResponse) obj;

    boolean result = true;
    result = result && (getSuccess()
        == other.getSuccess());
    result = result && getServerName()
        .equals(other.getServerName());
    result = result && (getOption()
        == other.getOption());
    result = result && (hasExecutionReply() == other.hasExecutionReply());
    if (hasExecutionReply()) {
      result = result && getExecutionReply()
          .equals(other.getExecutionReply());
    }
    result = result && unknownFields.equals(other.unknownFields);
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + SUCCESS_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
        getSuccess());
    hash = (37 * hash) + SERVERNAME_FIELD_NUMBER;
    hash = (53 * hash) + getServerName().hashCode();
    hash = (37 * hash) + OPTION_FIELD_NUMBER;
    hash = (53 * hash) + getOption();
    if (hasExecutionReply()) {
      hash = (37 * hash) + EXECUTIONREPLY_FIELD_NUMBER;
      hash = (53 * hash) + getExecutionReply().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static org.cse535.proto.TxnRelayResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.cse535.proto.TxnRelayResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.cse535.proto.TxnRelayResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.cse535.proto.TxnRelayResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.cse535.proto.TxnRelayResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.cse535.proto.TxnRelayResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.cse535.proto.TxnRelayResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.cse535.proto.TxnRelayResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.cse535.proto.TxnRelayResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static org.cse535.proto.TxnRelayResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.cse535.proto.TxnRelayResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.cse535.proto.TxnRelayResponse parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(org.cse535.proto.TxnRelayResponse prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code TxnRelayResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:TxnRelayResponse)
      org.cse535.proto.TxnRelayResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.cse535.proto.Linearpbft.internal_static_TxnRelayResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.cse535.proto.Linearpbft.internal_static_TxnRelayResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              org.cse535.proto.TxnRelayResponse.class, org.cse535.proto.TxnRelayResponse.Builder.class);
    }

    // Construct using org.cse535.proto.TxnRelayResponse.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      success_ = false;

      serverName_ = "";

      option_ = 0;

      if (executionReplyBuilder_ == null) {
        executionReply_ = null;
      } else {
        executionReply_ = null;
        executionReplyBuilder_ = null;
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return org.cse535.proto.Linearpbft.internal_static_TxnRelayResponse_descriptor;
    }

    @java.lang.Override
    public org.cse535.proto.TxnRelayResponse getDefaultInstanceForType() {
      return org.cse535.proto.TxnRelayResponse.getDefaultInstance();
    }

    @java.lang.Override
    public org.cse535.proto.TxnRelayResponse build() {
      org.cse535.proto.TxnRelayResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public org.cse535.proto.TxnRelayResponse buildPartial() {
      org.cse535.proto.TxnRelayResponse result = new org.cse535.proto.TxnRelayResponse(this);
      result.success_ = success_;
      result.serverName_ = serverName_;
      result.option_ = option_;
      if (executionReplyBuilder_ == null) {
        result.executionReply_ = executionReply_;
      } else {
        result.executionReply_ = executionReplyBuilder_.build();
      }
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return (Builder) super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof org.cse535.proto.TxnRelayResponse) {
        return mergeFrom((org.cse535.proto.TxnRelayResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(org.cse535.proto.TxnRelayResponse other) {
      if (other == org.cse535.proto.TxnRelayResponse.getDefaultInstance()) return this;
      if (other.getSuccess() != false) {
        setSuccess(other.getSuccess());
      }
      if (!other.getServerName().isEmpty()) {
        serverName_ = other.serverName_;
        onChanged();
      }
      if (other.getOption() != 0) {
        setOption(other.getOption());
      }
      if (other.hasExecutionReply()) {
        mergeExecutionReply(other.getExecutionReply());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      org.cse535.proto.TxnRelayResponse parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (org.cse535.proto.TxnRelayResponse) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private boolean success_ ;
    /**
     * <code>bool success = 1;</code>
     */
    public boolean getSuccess() {
      return success_;
    }
    /**
     * <code>bool success = 1;</code>
     */
    public Builder setSuccess(boolean value) {
      
      success_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bool success = 1;</code>
     */
    public Builder clearSuccess() {
      
      success_ = false;
      onChanged();
      return this;
    }

    private java.lang.Object serverName_ = "";
    /**
     * <code>string serverName = 2;</code>
     */
    public java.lang.String getServerName() {
      java.lang.Object ref = serverName_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        serverName_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string serverName = 2;</code>
     */
    public com.google.protobuf.ByteString
        getServerNameBytes() {
      java.lang.Object ref = serverName_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        serverName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string serverName = 2;</code>
     */
    public Builder setServerName(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      serverName_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string serverName = 2;</code>
     */
    public Builder clearServerName() {
      
      serverName_ = getDefaultInstance().getServerName();
      onChanged();
      return this;
    }
    /**
     * <code>string serverName = 2;</code>
     */
    public Builder setServerNameBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      serverName_ = value;
      onChanged();
      return this;
    }

    private int option_ ;
    /**
     * <code>int32 option = 3;</code>
     */
    public int getOption() {
      return option_;
    }
    /**
     * <code>int32 option = 3;</code>
     */
    public Builder setOption(int value) {
      
      option_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 option = 3;</code>
     */
    public Builder clearOption() {
      
      option_ = 0;
      onChanged();
      return this;
    }

    private org.cse535.proto.ExecutionReplyRequest executionReply_ = null;
    private com.google.protobuf.SingleFieldBuilderV3<
        org.cse535.proto.ExecutionReplyRequest, org.cse535.proto.ExecutionReplyRequest.Builder, org.cse535.proto.ExecutionReplyRequestOrBuilder> executionReplyBuilder_;
    /**
     * <code>.ExecutionReplyRequest executionReply = 4;</code>
     */
    public boolean hasExecutionReply() {
      return executionReplyBuilder_ != null || executionReply_ != null;
    }
    /**
     * <code>.ExecutionReplyRequest executionReply = 4;</code>
     */
    public org.cse535.proto.ExecutionReplyRequest getExecutionReply() {
      if (executionReplyBuilder_ == null) {
        return executionReply_ == null ? org.cse535.proto.ExecutionReplyRequest.getDefaultInstance() : executionReply_;
      } else {
        return executionReplyBuilder_.getMessage();
      }
    }
    /**
     * <code>.ExecutionReplyRequest executionReply = 4;</code>
     */
    public Builder setExecutionReply(org.cse535.proto.ExecutionReplyRequest value) {
      if (executionReplyBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        executionReply_ = value;
        onChanged();
      } else {
        executionReplyBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.ExecutionReplyRequest executionReply = 4;</code>
     */
    public Builder setExecutionReply(
        org.cse535.proto.ExecutionReplyRequest.Builder builderForValue) {
      if (executionReplyBuilder_ == null) {
        executionReply_ = builderForValue.build();
        onChanged();
      } else {
        executionReplyBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.ExecutionReplyRequest executionReply = 4;</code>
     */
    public Builder mergeExecutionReply(org.cse535.proto.ExecutionReplyRequest value) {
      if (executionReplyBuilder_ == null) {
        if (executionReply_ != null) {
          executionReply_ =
            org.cse535.proto.ExecutionReplyRequest.newBuilder(executionReply_).mergeFrom(value).buildPartial();
        } else {
          executionReply_ = value;
        }
        onChanged();
      } else {
        executionReplyBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.ExecutionReplyRequest executionReply = 4;</code>
     */
    public Builder clearExecutionReply() {
      if (executionReplyBuilder_ == null) {
        executionReply_ = null;
        onChanged();
      } else {
        executionReply_ = null;
        executionReplyBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.ExecutionReplyRequest executionReply = 4;</code>
     */
    public org.cse535.proto.ExecutionReplyRequest.Builder getExecutionReplyBuilder() {
      
      onChanged();
      return getExecutionReplyFieldBuilder().getBuilder();
    }
    /**
     * <code>.ExecutionReplyRequest executionReply = 4;</code>
     */
    public org.cse535.proto.ExecutionReplyRequestOrBuilder getExecutionReplyOrBuilder() {
      if (executionReplyBuilder_ != null) {
        return executionReplyBuilder_.getMessageOrBuilder();
      } else {
        return executionReply_ == null ?
            org.cse535.proto.ExecutionReplyRequest.getDefaultInstance() : executionReply_;
      }
    }
    /**
     * <code>.ExecutionReplyRequest executionReply = 4;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        org.cse535.proto.ExecutionReplyRequest, org.cse535.proto.ExecutionReplyRequest.Builder, org.cse535.proto.ExecutionReplyRequestOrBuilder> 
        getExecutionReplyFieldBuilder() {
      if (executionReplyBuilder_ == null) {
        executionReplyBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            org.cse535.proto.ExecutionReplyRequest, org.cse535.proto.ExecutionReplyRequest.Builder, org.cse535.proto.ExecutionReplyRequestOrBuilder>(
                getExecutionReply(),
                getParentForChildren(),
                isClean());
        executionReply_ = null;
      }
      return executionReplyBuilder_;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFieldsProto3(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:TxnRelayResponse)
  }

  // @@protoc_insertion_point(class_scope:TxnRelayResponse)
  private static final org.cse535.proto.TxnRelayResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new org.cse535.proto.TxnRelayResponse();
  }

  public static org.cse535.proto.TxnRelayResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<TxnRelayResponse>
      PARSER = new com.google.protobuf.AbstractParser<TxnRelayResponse>() {
    @java.lang.Override
    public TxnRelayResponse parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new TxnRelayResponse(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<TxnRelayResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<TxnRelayResponse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public org.cse535.proto.TxnRelayResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

