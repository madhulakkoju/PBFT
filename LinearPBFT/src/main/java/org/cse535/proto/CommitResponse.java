// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: linearpbft.proto

package org.cse535.proto;

/**
 * Protobuf type {@code CommitResponse}
 */
public  final class CommitResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:CommitResponse)
    CommitResponseOrBuilder {
private static final long serialVersionUID = 0L;
  // Use CommitResponse.newBuilder() to construct.
  private CommitResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private CommitResponse() {
    view_ = 0;
    sequenceNumber_ = 0;
    processId_ = "";
    success_ = false;
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private CommitResponse(
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

            view_ = input.readInt32();
            break;
          }
          case 16: {

            sequenceNumber_ = input.readInt32();
            break;
          }
          case 26: {
            java.lang.String s = input.readStringRequireUtf8();

            processId_ = s;
            break;
          }
          case 32: {

            success_ = input.readBool();
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
    return org.cse535.proto.Linearpbft.internal_static_CommitResponse_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return org.cse535.proto.Linearpbft.internal_static_CommitResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            org.cse535.proto.CommitResponse.class, org.cse535.proto.CommitResponse.Builder.class);
  }

  public static final int VIEW_FIELD_NUMBER = 1;
  private int view_;
  /**
   * <code>int32 view = 1;</code>
   */
  public int getView() {
    return view_;
  }

  public static final int SEQUENCENUMBER_FIELD_NUMBER = 2;
  private int sequenceNumber_;
  /**
   * <code>int32 sequenceNumber = 2;</code>
   */
  public int getSequenceNumber() {
    return sequenceNumber_;
  }

  public static final int PROCESSID_FIELD_NUMBER = 3;
  private volatile java.lang.Object processId_;
  /**
   * <code>string processId = 3;</code>
   */
  public java.lang.String getProcessId() {
    java.lang.Object ref = processId_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      processId_ = s;
      return s;
    }
  }
  /**
   * <code>string processId = 3;</code>
   */
  public com.google.protobuf.ByteString
      getProcessIdBytes() {
    java.lang.Object ref = processId_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      processId_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int SUCCESS_FIELD_NUMBER = 4;
  private boolean success_;
  /**
   * <code>bool success = 4;</code>
   */
  public boolean getSuccess() {
    return success_;
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
    if (view_ != 0) {
      output.writeInt32(1, view_);
    }
    if (sequenceNumber_ != 0) {
      output.writeInt32(2, sequenceNumber_);
    }
    if (!getProcessIdBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 3, processId_);
    }
    if (success_ != false) {
      output.writeBool(4, success_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (view_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(1, view_);
    }
    if (sequenceNumber_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(2, sequenceNumber_);
    }
    if (!getProcessIdBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, processId_);
    }
    if (success_ != false) {
      size += com.google.protobuf.CodedOutputStream
        .computeBoolSize(4, success_);
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
    if (!(obj instanceof org.cse535.proto.CommitResponse)) {
      return super.equals(obj);
    }
    org.cse535.proto.CommitResponse other = (org.cse535.proto.CommitResponse) obj;

    boolean result = true;
    result = result && (getView()
        == other.getView());
    result = result && (getSequenceNumber()
        == other.getSequenceNumber());
    result = result && getProcessId()
        .equals(other.getProcessId());
    result = result && (getSuccess()
        == other.getSuccess());
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
    hash = (37 * hash) + VIEW_FIELD_NUMBER;
    hash = (53 * hash) + getView();
    hash = (37 * hash) + SEQUENCENUMBER_FIELD_NUMBER;
    hash = (53 * hash) + getSequenceNumber();
    hash = (37 * hash) + PROCESSID_FIELD_NUMBER;
    hash = (53 * hash) + getProcessId().hashCode();
    hash = (37 * hash) + SUCCESS_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
        getSuccess());
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static org.cse535.proto.CommitResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.cse535.proto.CommitResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.cse535.proto.CommitResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.cse535.proto.CommitResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.cse535.proto.CommitResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.cse535.proto.CommitResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.cse535.proto.CommitResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.cse535.proto.CommitResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.cse535.proto.CommitResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static org.cse535.proto.CommitResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.cse535.proto.CommitResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.cse535.proto.CommitResponse parseFrom(
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
  public static Builder newBuilder(org.cse535.proto.CommitResponse prototype) {
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
   * Protobuf type {@code CommitResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:CommitResponse)
      org.cse535.proto.CommitResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.cse535.proto.Linearpbft.internal_static_CommitResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.cse535.proto.Linearpbft.internal_static_CommitResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              org.cse535.proto.CommitResponse.class, org.cse535.proto.CommitResponse.Builder.class);
    }

    // Construct using org.cse535.proto.CommitResponse.newBuilder()
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
      view_ = 0;

      sequenceNumber_ = 0;

      processId_ = "";

      success_ = false;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return org.cse535.proto.Linearpbft.internal_static_CommitResponse_descriptor;
    }

    @java.lang.Override
    public org.cse535.proto.CommitResponse getDefaultInstanceForType() {
      return org.cse535.proto.CommitResponse.getDefaultInstance();
    }

    @java.lang.Override
    public org.cse535.proto.CommitResponse build() {
      org.cse535.proto.CommitResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public org.cse535.proto.CommitResponse buildPartial() {
      org.cse535.proto.CommitResponse result = new org.cse535.proto.CommitResponse(this);
      result.view_ = view_;
      result.sequenceNumber_ = sequenceNumber_;
      result.processId_ = processId_;
      result.success_ = success_;
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
      if (other instanceof org.cse535.proto.CommitResponse) {
        return mergeFrom((org.cse535.proto.CommitResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(org.cse535.proto.CommitResponse other) {
      if (other == org.cse535.proto.CommitResponse.getDefaultInstance()) return this;
      if (other.getView() != 0) {
        setView(other.getView());
      }
      if (other.getSequenceNumber() != 0) {
        setSequenceNumber(other.getSequenceNumber());
      }
      if (!other.getProcessId().isEmpty()) {
        processId_ = other.processId_;
        onChanged();
      }
      if (other.getSuccess() != false) {
        setSuccess(other.getSuccess());
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
      org.cse535.proto.CommitResponse parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (org.cse535.proto.CommitResponse) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private int view_ ;
    /**
     * <code>int32 view = 1;</code>
     */
    public int getView() {
      return view_;
    }
    /**
     * <code>int32 view = 1;</code>
     */
    public Builder setView(int value) {
      
      view_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 view = 1;</code>
     */
    public Builder clearView() {
      
      view_ = 0;
      onChanged();
      return this;
    }

    private int sequenceNumber_ ;
    /**
     * <code>int32 sequenceNumber = 2;</code>
     */
    public int getSequenceNumber() {
      return sequenceNumber_;
    }
    /**
     * <code>int32 sequenceNumber = 2;</code>
     */
    public Builder setSequenceNumber(int value) {
      
      sequenceNumber_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 sequenceNumber = 2;</code>
     */
    public Builder clearSequenceNumber() {
      
      sequenceNumber_ = 0;
      onChanged();
      return this;
    }

    private java.lang.Object processId_ = "";
    /**
     * <code>string processId = 3;</code>
     */
    public java.lang.String getProcessId() {
      java.lang.Object ref = processId_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        processId_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string processId = 3;</code>
     */
    public com.google.protobuf.ByteString
        getProcessIdBytes() {
      java.lang.Object ref = processId_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        processId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string processId = 3;</code>
     */
    public Builder setProcessId(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      processId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string processId = 3;</code>
     */
    public Builder clearProcessId() {
      
      processId_ = getDefaultInstance().getProcessId();
      onChanged();
      return this;
    }
    /**
     * <code>string processId = 3;</code>
     */
    public Builder setProcessIdBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      processId_ = value;
      onChanged();
      return this;
    }

    private boolean success_ ;
    /**
     * <code>bool success = 4;</code>
     */
    public boolean getSuccess() {
      return success_;
    }
    /**
     * <code>bool success = 4;</code>
     */
    public Builder setSuccess(boolean value) {
      
      success_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bool success = 4;</code>
     */
    public Builder clearSuccess() {
      
      success_ = false;
      onChanged();
      return this;
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


    // @@protoc_insertion_point(builder_scope:CommitResponse)
  }

  // @@protoc_insertion_point(class_scope:CommitResponse)
  private static final org.cse535.proto.CommitResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new org.cse535.proto.CommitResponse();
  }

  public static org.cse535.proto.CommitResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<CommitResponse>
      PARSER = new com.google.protobuf.AbstractParser<CommitResponse>() {
    @java.lang.Override
    public CommitResponse parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new CommitResponse(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<CommitResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<CommitResponse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public org.cse535.proto.CommitResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

