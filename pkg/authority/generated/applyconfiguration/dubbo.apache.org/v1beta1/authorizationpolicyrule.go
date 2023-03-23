// Licensed to the Apache Software Foundation (ASF) under one or more
// contributor license agreements.  See the NOTICE file distributed with
// this work for additional information regarding copyright ownership.
// The ASF licenses this file to You under the Apache License, Version 2.0
// (the "License"); you may not use this file except in compliance with
// the License.  You may obtain a copy of the License at
//
//	http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

// Code generated by applyconfiguration-gen. DO NOT EDIT.

package v1beta1

// AuthorizationPolicyRuleApplyConfiguration represents an declarative configuration of the AuthorizationPolicyRule type for use
// with apply.
type AuthorizationPolicyRuleApplyConfiguration struct {
	From *AuthorizationPolicySourceApplyConfiguration    `json:"from,omitempty"`
	To   *AuthorizationPolicyTargetApplyConfiguration    `json:"to,omitempty"`
	When *AuthorizationPolicyConditionApplyConfiguration `json:"when,omitempty"`
}

// AuthorizationPolicyRuleApplyConfiguration constructs an declarative configuration of the AuthorizationPolicyRule type for use with
// apply.
func AuthorizationPolicyRule() *AuthorizationPolicyRuleApplyConfiguration {
	return &AuthorizationPolicyRuleApplyConfiguration{}
}

// WithFrom sets the From field in the declarative configuration to the given value
// and returns the receiver, so that objects can be built by chaining "With" function invocations.
// If called multiple times, the From field is set to the value of the last call.
func (b *AuthorizationPolicyRuleApplyConfiguration) WithFrom(value *AuthorizationPolicySourceApplyConfiguration) *AuthorizationPolicyRuleApplyConfiguration {
	b.From = value
	return b
}

// WithTo sets the To field in the declarative configuration to the given value
// and returns the receiver, so that objects can be built by chaining "With" function invocations.
// If called multiple times, the To field is set to the value of the last call.
func (b *AuthorizationPolicyRuleApplyConfiguration) WithTo(value *AuthorizationPolicyTargetApplyConfiguration) *AuthorizationPolicyRuleApplyConfiguration {
	b.To = value
	return b
}

// WithWhen sets the When field in the declarative configuration to the given value
// and returns the receiver, so that objects can be built by chaining "With" function invocations.
// If called multiple times, the When field is set to the value of the last call.
func (b *AuthorizationPolicyRuleApplyConfiguration) WithWhen(value *AuthorizationPolicyConditionApplyConfiguration) *AuthorizationPolicyRuleApplyConfiguration {
	b.When = value
	return b
}